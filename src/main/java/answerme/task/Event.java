package answerme.task;

import java.time.LocalDateTime;

import answerme.parser.DateTimeParser;

/**
 * Represents a task that occurs between a specified start and end time.
 */
public class Event extends Task {
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    /**
     * Creates an event task with the specified description and duration.
     *
     * @param description The event task description.
     * @param startTime The event's start date and time.
     * @param endTime The event's end date and time.
     */
    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // accessors
    public LocalDateTime getFrom() {
        return startTime;
    }

    public LocalDateTime getTo() {
        return endTime;
    }

    public void setFrom(LocalDateTime newFrom) {
        startTime = newFrom;
    }

    public void setTo(LocalDateTime newTo) {
        endTime = newTo;
    }

    /**
     * {@inheritDoc}
     *
     * Returns the event's fields in a human-readable format.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeParser.dateTimeToString(startTime)
                + " to: " + DateTimeParser.dateTimeToString(endTime) + ")";
    }

    /**
     * {@inheritDoc}
     *
     * Returns the event in storage format.
     */
    @Override
    public String toStorageFormat() {
        return getLogFormat("Event") + " | " + DateTimeParser.dateTimeToString(startTime)
                + " | " + DateTimeParser.dateTimeToString(endTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Event other) {
            return description.equals(other.description) &&
                    startTime.equals(other.startTime) &&
                    endTime.equals(other.endTime);
        }
        return false;
    }
}
