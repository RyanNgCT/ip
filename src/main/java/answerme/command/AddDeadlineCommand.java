package answerme.command;

import java.time.LocalDateTime;

import answerme.task.Deadline;

public class AddDeadlineCommand extends AddCommand {
    public AddDeadlineCommand(String description, LocalDateTime by) {
        super(new Deadline(description, by));
    }
}
