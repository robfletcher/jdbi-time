package co.freeside.jdbi.time

import java.time.LocalDate
import org.skife.jdbi.v2.tweak.ResultSetMapper
import org.skife.jdbi.v2.util.StringMapper

class LocalDateSpec extends TypeSpecification<LocalDate, String> {

  def setup() {
    handle.with {
      registerArgumentFactory new LocalDateArgumentFactory()
      registerMapper new LocalDateMapper()
    }
  }

  @Override
  protected Class<LocalDate> targetType() {
    LocalDate
  }

  @Override
  protected String columnSqlType() {
    "char(10)"
  }

  @Override
  protected ResultSetMapper<LocalDate> mapperForColumn(String name) {
    new LocalDateMapper(name)
  }

  @Override
  protected ResultSetMapper<LocalDate> mapperForFirstColumn() {
    LocalDateMapper.FIRST
  }

  @Override
  protected ResultSetMapper<String> mapperForSqlTypeFirstColumn() {
    StringMapper.FIRST
  }

  @Override
  protected LocalDate targetValue() {
    LocalDate.now()
  }

  @Override
  protected String toSqlType(LocalDate value) {
    value.toString()
  }
}
