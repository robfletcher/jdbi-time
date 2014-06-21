package co.freeside.jdbi.time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.skife.jdbi.v2.util.TypedMapper;

public class LocalDateMapper extends TypedMapper<LocalDate> {

    public static LocalDateMapper FIRST = new LocalDateMapper(1);

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
