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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import org.skife.jdbi.v2.util.TypedMapper;

/**
 * A +ResultSetMapper+ for retrieving +Duration+ values from a SQL query.
 *
 * @see java.time.Duration
 */
class DurationMapper extends TypedMapper<Duration> {

  /**
   * An instance which extracts value from the first field.
   */
  public static final DurationMapper FIRST = new DurationMapper(1);

  /**
   * Create a new instance which extracts the value from the first column.
   */
  public DurationMapper() {
  }

  /**
   * Create a new instance which extracts the value positionally
   * in the +ResultSet+.
   *
   * @param index 1 based column index into the +ResultSet+.
   */
  public DurationMapper(int index) {
    super(index);
  }

  /**
   * Create a new instance which extracts the value by name or alias from the
   * +ResultSet+.
   *
   * @param name The name or alias for the field.
   */
  public DurationMapper(String name) {
    super(name);
  }

  @Override
  protected Duration extractByName(ResultSet r, String name) throws SQLException {
    return Duration.ofMillis(r.getLong(name));
  }

  @Override
  protected Duration extractByIndex(ResultSet r, int index) throws SQLException {
    return Duration.ofMillis(r.getLong(index));
  }
}
