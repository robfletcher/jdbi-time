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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import javax.annotation.Nullable;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

/**
 * An +Argument+ implementation for any type that is represented as a +VARCHAR+
 * value on the database based on the object's +toString+ method.
 *
 * @param <T> the specific type.
 * @see Types#VARCHAR
 */
public class ToStringArgument<T> extends NullSafeArgument<T> {

  /**
   * Constructs a new +Argument+ for the specified value which may be +null+.
   *
   * @param value a value that may be +null+.
   * @param <T>   the type of +value+.
   * @return a new +Argument+ for binding +value+ to a +PreparedStatement+.
   */
  public static <T> Argument of(@Nullable T value) {
    return new ToStringArgument<>(value);
  }

  public ToStringArgument(@Nullable T value) {
    super(value, Types.VARCHAR);
  }

  @Override
  protected void applyNotNull(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
    statement.setString(position, value.toString());
  }
}
