/*
 * Copyright 2014 Rob Fletcher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.freeside.jdbi.time

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.tweak.ResultSetMapper
import org.skife.jdbi.v2.util.LongMapper

/**
 * Base for all specifications that test support for individual +java.time+ types.
 * @param < Target >   the +java.time+ type being tested.
 * @param < SqlType >   the simple type corresponding to the type of column.
 */
@Unroll
abstract class BaseSpecification<Target, ColumnType> extends Specification {

  @AutoCleanup handle = DBI.open("jdbc:h2:mem:test")
  @Shared fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault())

  /**
   * @return the +java.time+ class being tested.
   */
  protected abstract Class<Target> targetType()

  /**
   * @return the SQL type of the column.
   */
  protected abstract String columnSqlType()

  /**
   * @param name the column name.
   * @return a mapper for the named column to the +java.time+ type.
   */
  protected abstract ResultSetMapper<Target> targetTypeMapperFor(String name)

  /**
   * @return a mapper for the first column to the +java.time+ type.
   */
  protected abstract ResultSetMapper<Target> targetTypeMapperForFirst()

  /**
   * @return an example value.
   */
  protected abstract Target targetValue()

  /**
   * @param value a +java.time+ value
   * @return the expected representation of +value+ in the database.
   */
  protected abstract ColumnType toColumnType(Target value)

  /**
   * @return a mapper for the basic column type. Used to check the value inserted to the database.
   */
  protected abstract ResultSetMapper<ColumnType> columnTypeMapperForFirst()

  def setup() {
    handle.with {
      registerMapper new TimeTypesMapperFactory()
      createStatement("create table a_table (id bigint primary key auto_increment, value ${columnSqlType()})").execute()
    }
  }

  def cleanup() {
    handle.createStatement("drop table a_table if exists").execute()
  }

  def "can insert an #value.class.simpleName to a #expected.class.simpleName column"() {
    when:
    def id = insertAndReturnGeneratedKey(value)

    then:
    handle.createQuery(selectByIdSql())
      .bind("id", id)
      .map(columnTypeMapperForFirst())
      .first() == expected

    where:
    value = targetValue()
    expected = toColumnType(value)
  }

  def "can read a #value.class.simpleName column into an #expected.class.simpleName"() {
    given:
    def id = insertAndReturnGeneratedKey(value)

    when:
    def result = handle.createQuery(selectByIdSql())
      .bind("id", id)
      .mapTo(targetType())
      .first()

    then:
    result == expected

    where:
    value = toColumnType(targetValue())
    expected = targetValue()
  }

  def "can read a named #value.class.simpleName column into an #expected.class.simpleName"() {
    given:
    def id = insertAndReturnGeneratedKey(value)

    when:
    def result = handle.createQuery(selectByIdSql())
      .bind("id", id)
      .map(targetTypeMapperFor("value"))
      .first()

    then:
    result == expected

    where:
    value = toColumnType(targetValue())
    expected = targetValue()
  }

  def "can read an indexed #value.class.simpleName column into an #expected.class.simpleName"() {
    given:
    def id = insertAndReturnGeneratedKey(value)

    when:
    def result = handle.createQuery(selectByIdSql())
      .bind("id", id)
      .map(targetTypeMapperForFirst())
      .first()

    then:
    result == expected

    where:
    value = toColumnType(targetValue())
    expected = targetValue()
  }

  protected long insertAndReturnGeneratedKey(value) {
    handle.createStatement(insertSql())
      .bind("value", value)
      .executeAndReturnGeneratedKeys(LongMapper.FIRST)
      .first()
  }

  protected String insertSql() {
    "insert into a_table (value) values (:value)"
  }

  protected String selectByIdSql() {
    "select value from a_table where id = :id"
  }
}
