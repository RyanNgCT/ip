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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof Deadline other) {
            return description.equals(other.description) &&
                    by.equals(other.by);
        }
        return true;
    }
}
