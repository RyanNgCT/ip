package answerme.command;

import answerme.task.Deadline;
import java.time.LocalDateTime;

/**
 * Represents a command that adds a deadline task to the task list.
 */
public class AddDeadlineCommand extends AddCommand {
    /**
     * Creates a command that adds a deadline task with the specified description
     * and deadline.
     *
     * @param description The description of the deadline task.
     * @param by The deadline date and time.
     */
    public AddDeadlineCommand(String description, LocalDateTime by) {
        super(new Deadline(description, by));
    }
}
