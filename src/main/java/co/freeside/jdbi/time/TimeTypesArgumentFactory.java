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

package co.freeside.jdbi.time;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.HashSet;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

public class TimeTypesArgumentFactory implements ArgumentFactory<Object> {

  private static final Collection<Class> SUPPORTED_TYPES = new HashSet<>();

  static {
    SUPPORTED_TYPES.add(Instant.class);
    SUPPORTED_TYPES.add(LocalDate.class);
    SUPPORTED_TYPES.add(LocalDateTime.class);
    SUPPORTED_TYPES.add(LocalTime.class);
    SUPPORTED_TYPES.add(MonthDay.class);
    SUPPORTED_TYPES.add(YearMonth.class);
    SUPPORTED_TYPES.add(Duration.class);
    SUPPORTED_TYPES.add(Period.class);
  }

  @Override
  public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
    return SUPPORTED_TYPES.contains(value.getClass());
  }

  @Override
  public Argument build(Class<?> expectedType, Object value, StatementContext ctx) {
    if (expectedType.equals(Instant.class)) {
      return new InstantArgument((Instant) value);
    } else if (expectedType.equals(Duration.class)) {
      return new DurationArgument((Duration) value);
    } else if (expectedType.equals(Period.class)) {
      return new PeriodArgument((Period) value);
    } else if (TemporalAccessor.class.isAssignableFrom(expectedType)) {
      return TemporalAsStringArgument.of((TemporalAccessor) value);
    } else {
      throw new IllegalArgumentException(String.format("%s is not a supported type", expectedType));
    }
  }
}
