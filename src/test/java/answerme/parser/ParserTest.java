package answerme.parser;

import answerme.command.Command;
import answerme.exception.AnswerMeException;
import answerme.command.AddEventCommand;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    @Test
    public void addNewEvent_allParamsPresent_success() throws AnswerMeException {
        String newEvent = "event wedding dinner /from 31/8/2026 17:00 /to 1/9/2026 02:00";
        LocalDateTime d1 = LocalDateTime.of(2026,8,31,17,00);
        LocalDateTime d2 = LocalDateTime.of(2026,9,1,02,00);
        assertEquals(Parser.parse(newEvent), new AddEventCommand("wedding dinner", d1, d2));
    }

    @Test
    public void addNewEvent_missingDescription_exceptionThrown() {
        String newEvent = "event /from 31/8/2026 17:00 /to 1/9/2026 02:00";
        LocalDateTime d1 = LocalDateTime.of(2026,8,31,17,00);
        LocalDateTime d2 = LocalDateTime.of(2026,9,1,02,00);
        try {
            Command eventCmd = Parser.parse(newEvent);
            fail();
        } catch (AnswerMeException e) {
            assertEquals("Oh no! Format: event <description> /from <when> /to <when>", e.getMessage());
        }
    }
}
