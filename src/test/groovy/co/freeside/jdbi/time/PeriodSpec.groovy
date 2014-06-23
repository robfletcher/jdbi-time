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

import java.time.Period
import org.skife.jdbi.v2.tweak.ResultSetMapper

class PeriodSpec extends MapsToStringSpecification<Period> {

  def setup() {
    handle.registerArgumentFactory new PeriodArgumentFactory()
  }

  @Override
  protected Class<Period> targetType() {
    Period
  }

  @Override
  protected ResultSetMapper<Period> targetTypeMapperFor(String name) {
    new PeriodMapper(name)
  }

  @Override
  protected ResultSetMapper<Period> targetTypeMapperForFirst() {
    PeriodMapper.FIRST
  }

  @Override
  protected Period targetValue() {
    Period.of(3, 2, 1)
  }
}
