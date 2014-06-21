package co.freeside.jdbi.time

import java.time.Instant
import org.skife.jdbi.v2.tweak.ResultSetMapper

class InstantSpec extends TypeSpecification<Instant> {

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
}
