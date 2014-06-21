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

import spock.lang.AutoCleanup
import spock.lang.Specification
import spock.lang.Unroll
import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.tweak.ResultSetMapper
import org.skife.jdbi.v2.util.LongMapper

@Unroll
abstract class TypeSpecification<Target, SqlType> extends Specification {

  @AutoCleanup protected handle = DBI.open("jdbc:h2:mem:test")

  protected abstract Class<Target> targetType()

  protected abstract String columnSqlType()

  protected abstract ResultSetMapper<Target> mapperForColumn(String name)

  protected abstract ResultSetMapper<Target> mapperForFirstColumn()

  protected abstract Target targetValue()

  protected abstract SqlType toSqlType(Target value)

  protected abstract ResultSetMapper<SqlType> mapperForSqlTypeFirstColumn()

  def setup() {
    handle.createStatement("create table a_table (id bigint primary key auto_increment, value ${columnSqlType()})").execute()
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
      .map(mapperForSqlTypeFirstColumn())
      .first() == expected

    where:
    value = targetValue()
    expected = toSqlType(value)
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
    value = toSqlType(targetValue())
    expected = targetValue()
  }

  def "can read a named #value.class.simpleName column into an #expected.class.simpleName"() {
    given:
    def id = insertAndReturnGeneratedKey(value)

    when:
    def result = handle.createQuery(selectByIdSql())
      .bind("id", id)
      .map(mapperForColumn("value"))
      .first()

    then:
    result == expected

    where:
    value = toSqlType(targetValue())
    expected = targetValue()
  }

  def "can read an indexed #value.class.simpleName column into an #expected.class.simpleName"() {
    given:
    def id = insertAndReturnGeneratedKey(value)

    when:
    def result = handle.createQuery(selectByIdSql())
      .bind("id", id)
      .map(mapperForFirstColumn())
      .first()

    then:
    result == expected

    where:
    value = toSqlType(targetValue())
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
