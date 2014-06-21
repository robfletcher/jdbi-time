package co.freeside.jdbi.time

import java.time.*
import org.skife.jdbi.v2.*
import org.skife.jdbi.v2.util.*
import spock.lang.*

class InstantSpec extends Specification {

  @AutoCleanup handle = DBI.open("jdbc:h2:mem:test")

  def setup() {
    handle.registerArgumentFactory(new InstantArgumentFactory())
    handle.createStatement("create table a_table (id bigint primary key auto_increment, value timestamp)").execute()
  }

  def cleanup() {
    handle.createStatement("drop table a_table if exists").execute()
  }

  def "can insert data that includes an Instant"() {
    when:
    def id = handle.createStatement("insert into a_table (value) values (:value)")
      .bind("value", value)
      .executeAndReturnGeneratedKeys(LongMapper.FIRST)
      .first()

    then:
    def stored = handle.createQuery("select value from a_table where id = :id")
      .bind("id", id)
      .map(TimestampMapper.FIRST)
      .first()
    stored.time == value.toEpochMilli()

    where:
    value = Instant.now()
  }

}
