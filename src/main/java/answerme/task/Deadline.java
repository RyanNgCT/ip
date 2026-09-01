package answerme.task;

import answerme.parser.DateTimeParser;
import java.time.LocalDateTime;

/**
 * Represents a task that must be completed by a specified date
 * and/or time.
 */
public class Deadline extends Task{
    protected LocalDateTime by;

    /**
     * Creates a deadline task with the specified description and deadline
     * (i.e. {@code by}).
     *
     * @param description The deadline's description.
     * @param by The date and time by which the deadline must be completed.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    // accessors
    public void setDeadLine(LocalDateTime newDeadline) {
        by = newDeadline;
    }

    public LocalDateTime getDueBy() {
        return by;
    }

    /**
     * {@inheritDoc}
     *
     * Returns the deadline's fields in a human-readable format.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeParser.dtToString(by) + ")";
    }

    /**
     * {@inheritDoc}
     *
     * Returns the deadline in storage format.
     */
    @Override
    public String toStorageFormat() {
        return getLogFormat("Deadline") + " | " + DateTimeParser.dtToString(by);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof Deadline other) {
            return description.equals(other.description) &&
                    by.equals(other.by);
        }
        return false;
    }
}
