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
import java.time.Instant;
import org.skife.jdbi.v2.util.TypedMapper;

public class InstantMapper extends TypedMapper<Instant> {

  public static final InstantMapper FIRST = new InstantMapper(1);

  public InstantMapper() {
  }

  public InstantMapper(int index) {
    super(index);
  }

  public InstantMapper(String name) {
    super(name);
  }

  @Override
  protected Instant extractByName(final ResultSet r, final String name) throws SQLException {
    return Instant.ofEpochMilli(r.getTimestamp(name).getTime());
  }

  @Override
  protected Instant extractByIndex(final ResultSet r, final int index) throws SQLException {
    return Instant.ofEpochMilli(r.getTimestamp(index).getTime());
  }
}
