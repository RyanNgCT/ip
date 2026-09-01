package answerme.task;

import java.time.LocalDateTime;

import answerme.parser.DateTimeParser;

public class Deadline extends Task {
    protected LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    public void setDeadline(LocalDateTime newDeadline) {
        deadline = newDeadline;
    }

    public LocalDateTime getDueBy() {
        return deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeParser.dateTimeToString(deadline) + ")";
    }

    @Override
    public String toStorageFormat() {
        return getLogFormat("Deadline") + " | " + DateTimeParser.dateTimeToString(deadline);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Deadline other) {
            return description.equals(other.description) &&
                    deadline.equals(other.deadline);
        }
        return false;
    }
}
