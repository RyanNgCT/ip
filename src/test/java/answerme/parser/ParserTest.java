package answerme.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import answerme.command.AddEventCommand;
import answerme.command.Command;
import answerme.exception.AnswerMeException;

public class ParserTest {
    @Test
    public void addNewEvent_allParamsPresent_success() throws AnswerMeException {
        String newEvent = "event wedding dinner /from 31/8/2026 17:00 /to 1/9/2026 02:00";
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 31, 17, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 9, 1, 2, 0);
        assertEquals(Parser.parse(newEvent), new AddEventCommand("wedding dinner", startTime, endTime));
    }

    @Test
    public void addNewEvent_missingDescription_exceptionThrown() {
        String newEvent = "event /from 31/8/2026 17:00 /to 1/9/2026 02:00";
        try {
            Command eventCommand = Parser.parse(newEvent);
            fail();
        } catch (AnswerMeException e) {
            assertEquals("Oh no! Format: event <description> /from <when> /to <when>", e.getMessage());
        }
    }

    @Test
    public void addNewEvent_invalidDateTime_exceptionThrown() {
        String newEvent = "event wedding dinner /from 2-09-2026 1700 /to 3-09-2026 0200";
        try {
            Command eventCommand = Parser.parse(newEvent);
            fail();
        } catch (AnswerMeException e) {
            assertEquals("Oh no! Ensure date/time is formatted correctly.", e.getMessage());
        }
    }

    @Test
    public void addNewEvent_fromAfterTo_exceptionThrown() {
        String newEvent = "event wedding dinner /from 2/9/2026 17:00 /to 1/9/2026 02:00";
        try {
            Command eventCommand = Parser.parse(newEvent);
            fail();
        } catch (AnswerMeException e) {
            assertEquals("Oh no! 'From' datetime must occur before 'To'.", e.getMessage());
        }
    }
}
