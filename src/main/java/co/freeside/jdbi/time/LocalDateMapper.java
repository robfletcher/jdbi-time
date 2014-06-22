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
import java.time.LocalDate;
import org.skife.jdbi.v2.util.TypedMapper;

public class LocalDateMapper extends TypedMapper<LocalDate> {

  public static final LocalDateMapper FIRST = new LocalDateMapper(1);

  public LocalDateMapper() {
  }

  public LocalDateMapper(int index) {
    super(index);
  }

  public LocalDateMapper(String name) {
    super(name);
  }

  @Override
  protected LocalDate extractByName(ResultSet r, String name) throws SQLException {
    return LocalDate.parse(r.getString(name));
  }

  @Override
  protected LocalDate extractByIndex(ResultSet r, int index) throws SQLException {
    return LocalDate.parse(r.getString(index));
  }
}
