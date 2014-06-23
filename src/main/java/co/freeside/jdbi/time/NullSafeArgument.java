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
import javax.annotation.Nullable;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

/**
 * Base class for Argument implementations where the value being bound may be +null+.
 */
public abstract class NullSafeArgument<T> implements Argument {

  protected final T value;
  private final int sqlType;

  /**
   * @param value   the value being bound.
   * @param sqlType a constant from {@link java.sql.Types} representing the
   *                column type. Implementing classes will typically hard-code
   *                this.
   */
  protected NullSafeArgument(@Nullable T value, int sqlType) {
    this.value = value;
    this.sqlType = sqlType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
    if (value == null) {
      statement.setNull(position, sqlType);
    } else {
      applyNotNull(position, statement, ctx);
    }
  }

  /**
   * Invoked internally if the +value+ passed to the constructor was not +null+.
   *
   * @param position  the position to which the argument should be bound, using
   *                  the stupid JDBC "start at 1" bit
   * @param statement the prepared statement the argument is to be bound to
   * @param ctx
   * @throws SQLException if anything goes wrong
   */
  protected abstract void applyNotNull(int position, PreparedStatement statement, StatementContext ctx) throws SQLException;
}
