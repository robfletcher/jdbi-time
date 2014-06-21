/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.freeside.jdbi.time;

import java.sql.*;
import java.time.*;
import org.skife.jdbi.v2.*;
import org.skife.jdbi.v2.tweak.*;

/**
 * Supports the use of +java.time.Instant+ as an argument to a SQL call.
 */
public class InstantArgument implements Argument {

  private final Instant value;

  InstantArgument(final Instant value) {
    this.value = value;
  }

  @Override
  public void apply(final int position, final PreparedStatement statement, final StatementContext ctx) throws SQLException {
    if (value == null) {
      statement.setNull(position, Types.TIMESTAMP);
    } else {
      statement.setTimestamp(position, new Timestamp(value.toEpochMilli()));
    }
  }
}
