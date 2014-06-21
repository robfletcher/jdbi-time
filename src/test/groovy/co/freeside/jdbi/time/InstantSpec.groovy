package co.freeside.jdbi.time

import java.sql.Timestamp
import java.time.Instant
import spock.lang.AutoCleanup
import spock.lang.Specification
import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.util.LongMapper
import org.skife.jdbi.v2.util.TimestampMapper

class InstantSpec extends Specification {

  @AutoCleanup
    handle = DBI.open("jdbc:h2:mem:test")

  def setup() {
    handle.registerArgumentFactory(new InstantArgumentFactory())
    handle.registerMapper(new InstantMapper())
    handle.createStatement("create table a_table (id bigint primary key auto_increment, value timestamp)").execute()
  }

  def cleanup() {
    handle.createStatement("drop table a_table if exists").execute()
  }

  def "can insert an Instant to a Timestamp column"() {
    when:
    def id = handle.createStatement("insert into a_table (value) values (:value)")
      .bind("value", value)
      .executeAndReturnGeneratedKeys(LongMapper.FIRST)
      .first()

    then:
    handle.createQuery("select value from a_table where id = :id")
      .bind("id", id)
      .map(TimestampMapper.FIRST)
      .first() == expected

    where:
    value = Instant.now()
    expected = new Timestamp(value.toEpochMilli())
  }

  def "can read a Timestamp column into an Instant"() {
    given:
    def id = handle.createStatement("insert into a_table (value) values (:value)")
      .bind("value", value)
      .executeAndReturnGeneratedKeys(LongMapper.FIRST)
      .first()

    when:
    def result = handle.createQuery("select value from a_table where id = :id")
      .bind("id", id)
      .mapTo(Instant)
      .first()

    then:
    result == expected

    where:
    value = new Timestamp(System.currentTimeMillis())
    expected = Instant.ofEpochMilli(value.time)
  }

}
