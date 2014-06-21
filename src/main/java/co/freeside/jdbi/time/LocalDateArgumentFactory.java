package co.freeside.jdbi.time;

import java.time.LocalDate;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

public class LocalDateArgumentFactory implements ArgumentFactory<LocalDate> {
    @Override
    public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
        return value instanceof LocalDate;
    }

    @Override
    public Argument build(Class<?> expectedType, LocalDate value, StatementContext ctx) {
        return new LocalDateArgument(value);
    }
}
