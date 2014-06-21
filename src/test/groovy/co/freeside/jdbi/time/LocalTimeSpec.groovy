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

import java.time.LocalTime
import org.skife.jdbi.v2.tweak.ResultSetMapper

class LocalTimeSpec extends MapsToStringSpecification<LocalTime> {

    def setup() {
        handle.registerArgumentFactory new TemporalAsStringArgumentFactory(LocalTime)
    }

    @Override
    protected Class<LocalTime> targetType() {
        LocalTime
    }

    @Override
    protected ResultSetMapper<LocalTime> targetTypeMapperFor(String name) {
        new LocalTimeMapper(name)
    }

    @Override
    protected ResultSetMapper<LocalTime> targetTypeMapperForFirst() {
        LocalTimeMapper.FIRST
    }

    @Override
    protected LocalTime targetValue() {
        LocalTime.now()
    }

    @Override
    protected String toColumnType(LocalTime value) {
        value.toString()
    }
}
