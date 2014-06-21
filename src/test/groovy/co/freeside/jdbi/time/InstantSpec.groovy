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
