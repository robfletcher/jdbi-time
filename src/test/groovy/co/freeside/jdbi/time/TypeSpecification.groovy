package co.freeside.jdbi.time

import java.sql.Timestamp
import java.time.Instant
import spock.lang.AutoCleanup
import spock.lang.Specification
import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.tweak.ResultSetMapper
import org.skife.jdbi.v2.util.LongMapper
import org.skife.jdbi.v2.util.TimestampMapper

abstract class TypeSpecification<T> extends Specification {

  @AutoCleanup protected handle = DBI.open("jdbc:h2:mem:test")

  protected abstract Class<T> targetType()
  protected abstract String columnSqlType()
  protected abstract ResultSetMapper<T> mapperForColumn(String name)
  protected abstract ResultSetMapper<T> mapperForFirstColumn()

  def setup() {
    handle.createStatement("create table a_table (id bigint primary key auto_increment, value ${columnSqlType()})").execute()
  }

  def cleanup() {
    handle.createStatement("drop table a_table if exists").execute()
  }

  def "can insert an Instant to a Timestamp column"() {
    when:
    def id = insertAndReturnGeneratedKey(value)

    then:
    handle.createQuery(selectByIdSql())
      .bind("id", id)
      .map(TimestampMapper.FIRST)
      .first() == expected

    where:
    value = Instant.now()
    expected = new Timestamp(value.toEpochMilli())
  }

  def "can read a Timestamp column into an Instant"() {
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
    value = new Timestamp(System.currentTimeMillis())
    expected = Instant.ofEpochMilli(value.time)
  }

  def "can read a named Timestamp column into an Instant"() {
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
    value = new Timestamp(System.currentTimeMillis())
    expected = Instant.ofEpochMilli(value.time)
  }

  def "can read an indexed Timestamp column into an Instant"() {
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
    value = new Timestamp(System.currentTimeMillis())
    expected = Instant.ofEpochMilli(value.time)
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
