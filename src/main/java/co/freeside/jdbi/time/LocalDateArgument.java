package co.freeside.jdbi.time;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

public class LocalDateArgument implements Argument {

    private final LocalDate value;

    public LocalDateArgument(LocalDate value) {
        this.value = value;
    }

    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        if (value == null) {
            statement.setNull(position, Types.CHAR);
        } else {
            statement.setString(position, value.toString());
        }
    }
}
