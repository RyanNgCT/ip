package answerme.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import answerme.exception.AnswerMeException;

public class DateTimeParserTest {
    @Test
    public void parseTest_validDateFormat_success() throws AnswerMeException {
        String toParse = "31/8/2026 14:00";
        LocalDateTime expectedDateTime = LocalDateTime.of(2026, 8, 31, 14, 0);
        assertEquals(new DateTimeParser().parseDateTime(toParse), expectedDateTime);
    }

    @Test
    public void parseTest_invalidDateFormat_exceptionThrown() {
        String toParse = "31/8/26 14:67";
        try {
            new DateTimeParser().parseDateTime(toParse);
            fail();
        } catch (AnswerMeException e) {
            assertEquals("Oh no! Ensure date/time is formatted correctly.", e.getMessage());
        }
    }
}
