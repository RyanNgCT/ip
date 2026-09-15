package answerme.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import answerme.command.AddEventCommand;
import answerme.command.Command;
import answerme.exception.AnswerMeException;

public class ParserTest {
    private static final String PIPE_ERROR_MESSAGE = "Oh no! "
            + "Task description cannot contain pipe characters!";

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
        String newEvent = "event wedding dinner /from 2-09-2026 1700 /to 3-09-2026 0267";
        try {
            Command eventCommand = Parser.parse(newEvent);
            fail();
        } catch (AnswerMeException e) {
            assertEquals("Oh no! Ensure that date/time is formatted correctly.\n"
                            + "Use a date such as 11/9/2026 or a date and time such as 11/9/2026 1800.",
                            e.getMessage());
        }
    }

    @Test
    public void addNewEvent_fromAfterTo_exceptionThrown() {
        String newEvent = "event wedding dinner /from 2/9/2026 17:00 /to 1/9/2026 02:00";
        try {
            Command eventCommand = Parser.parse(newEvent);
            fail();
        } catch (AnswerMeException e) {
            assertEquals("Oh no! 'From' datetime cannot occur after 'To'.", e.getMessage());
        }
    }

    @Test
    public void addNewTodo_descriptionContainsStorageSeparator_exceptionThrown() {
        String newTodo = "todo research | draft outline";
        try {
            Parser.parse(newTodo);
            fail();
        } catch (AnswerMeException e) {
            assertEquals(PIPE_ERROR_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void addNewDeadline_descriptionContainsStorageSeparator_exceptionThrown() {
        String newDeadline = "deadline submit | report /by 1/1/2027";
        try {
            Parser.parse(newDeadline);
            fail();
        } catch (AnswerMeException e) {
            assertEquals(PIPE_ERROR_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void addNewEvent_descriptionContainsStorageSeparator_exceptionThrown() {
        String newEvent = "event team | sync /from 1/1/2027 0900 /to 1/1/2027 1000";
        try {
            Parser.parse(newEvent);
            fail();
        } catch (AnswerMeException e) {
            assertEquals(PIPE_ERROR_MESSAGE, e.getMessage());
        }
    }
}
