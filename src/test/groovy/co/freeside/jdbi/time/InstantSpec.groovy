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

class InstantSpec extends MapsToTimestampSpecification<Instant> {

  @Override
  protected Class<Instant> targetType() {
    Instant
  }

  @Override
  protected ResultSetMapper<Instant> targetTypeMapperFor(String name) {
    new InstantMapper(name)
  }

  @Override
  protected ResultSetMapper<Instant> targetTypeMapperForFirst() {
    InstantMapper.FIRST
  }

  @Override
  protected Instant targetValue() {
    Instant.now(fixedClock)
  }

  @Override
  protected Timestamp toColumnType(Instant value) {
    new Timestamp(value.toEpochMilli())
  }
}
