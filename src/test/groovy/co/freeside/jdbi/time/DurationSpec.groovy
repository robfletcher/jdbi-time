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

import java.time.Duration
import org.skife.jdbi.v2.tweak.ResultSetMapper
import org.skife.jdbi.v2.util.LongMapper

class DurationSpec extends BaseSpecification<Duration, Long> {

  @Override
  protected Class<Duration> targetType() {
    Duration
  }

  @Override
  protected String columnSqlType() {
    "bigint"
  }

  @Override
  protected ResultSetMapper<Duration> targetTypeMapperFor(String name) {
    new DurationMapper(name)
  }

  @Override
  protected ResultSetMapper<Duration> targetTypeMapperForFirst() {
    DurationMapper.FIRST
  }

  @Override
  protected Duration targetValue() {
    Duration.ofDays(1)
  }

  @Override
  protected Long toColumnType(Duration value) {
    value.toMillis()
  }

  @Override
  protected ResultSetMapper<Long> columnTypeMapperForFirst() {
    LongMapper.FIRST
  }
}
