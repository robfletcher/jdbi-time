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

import java.time.MonthDay
import org.skife.jdbi.v2.tweak.ResultSetMapper

class MonthDaySpec extends MapsToStringSpecification<MonthDay> {

  @Override
  protected Class<MonthDay> targetType() {
    MonthDay
  }

  @Override
  protected ResultSetMapper<MonthDay> targetTypeMapperFor(String name) {
    new MonthDayMapper(name)
  }

  @Override
  protected ResultSetMapper<MonthDay> targetTypeMapperForFirst() {
    MonthDayMapper.FIRST
  }

  @Override
  protected MonthDay targetValue() {
    MonthDay.now(fixedClock)
  }
}
