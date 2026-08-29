package answerme.task;

import answerme.parser.DateTimeParser;
import java.time.LocalDateTime;

public class Deadline extends Task{
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public void setDeadLine(LocalDateTime newDeadline) {
        by = newDeadline;
    }

    public LocalDateTime getDueBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeParser.dtToString(by) + ")";
    }

    @Override
    public String toStorageFormat() {
        return getLogFormat("Deadline") + " | " + DateTimeParser.dtToString(by);
    }
}
