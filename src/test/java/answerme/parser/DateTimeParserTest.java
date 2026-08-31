package answerme.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DateTimeParserTest {
    @Test
    public void parseTest_validDateFormat_success() {
        String toParse = "31/8/2026 14:00";
        LocalDateTime ldt = LocalDateTime.of(2026,8,31,14,00);
        assertEquals(new DateTimeParser().parse(toParse), ldt);
    }

    @Test
    public void parseTest_invalidDateFormat_exceptionThrown() {
        String toParse = "31/8/26 14:67";
        try {
            new DateTimeParser().parse(toParse);
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Invalid datetime format!", e.getMessage());
        }
    }
}
