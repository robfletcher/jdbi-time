package co.freeside.jdbi.time

import java.sql.Timestamp
import java.time.Instant
import org.skife.jdbi.v2.tweak.ResultSetMapper
import org.skife.jdbi.v2.util.TimestampMapper

class InstantSpec extends TypeSpecification<Instant, Timestamp> {

  def setup() {
    handle.with {
      registerArgumentFactory new InstantArgumentFactory()
      registerMapper new InstantMapper()
    }
  }

  @Override
  protected Class<Instant> targetType() {
    Instant
  }

  @Override
  protected String columnSqlType() {
    "timestamp"
  }

  @Override
  protected ResultSetMapper<Instant> mapperForColumn(String name) {
    new InstantMapper(name)
  }

  @Override
  protected ResultSetMapper<Instant> mapperForFirstColumn() {
    InstantMapper.FIRST
  }

  @Override
  protected ResultSetMapper<Timestamp> mapperForSqlTypeFirstColumn() {
    TimestampMapper.FIRST
  }

  @Override
  protected Instant targetValue() {
    Instant.now()
  }

  @Override
  protected Timestamp toSqlType(Instant value) {
    new Timestamp(value.toEpochMilli())
  }
}
