package answerme.task;

import answerme.parser.DateTimeParser;
import java.time.LocalDateTime;

/**
 * Represents a task that occurs between a specified start and end time.
 */
public class Event extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates an event task with the specified description and duration.
     *
     * @param description The event task description.
     * @param from The event's start date and time.
     * @param to The event's end date and time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    // accessors
    public LocalDateTime getFrom () {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setFrom(LocalDateTime newFrom) {
        from = newFrom;
    }

    public void setTo(LocalDateTime newTo) {
        to = newTo;
    }

    /**
     * {@inheritDoc}
     *
     * Returns the event's fields in a human-readable format.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeParser.dtToString(from) +
                " to: " + DateTimeParser.dtToString(to) + ")";
    }

    /**
     * {@inheritDoc}
     *
     * Returns the event in storage format.
     */
    @Override
    public String toStorageFormat() {
        return getLogFormat("Event") + " | " + DateTimeParser.dtToString(from) +
                " | " + DateTimeParser.dtToString(to);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof Event other) {
            return description.equals(other.description) &&
                    from.equals(other.from) &&
                    to.equals(other.to);
        }
        return false;
    }
}
