package answerme.task;

import java.time.LocalDateTime;

import answerme.parser.DateTimeParser;

/**
 * Represents a task that occurs between a specified start and end time.
 */
public class Event extends Task {
    protected LocalDateTime eventStart;
    protected LocalDateTime eventEnd;

    /**
     * Creates an event task with the specified description and duration.
     *
     * @param description The event task description.
     * @param eventStart The event's start date and time.
     * @param eventEnd The event's end date and time.
     */
    public Event(String description, LocalDateTime eventStart, LocalDateTime eventEnd) {
        super(description);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    // accessors
    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventStart(LocalDateTime newFrom) {
        eventStart = newFrom;
    }

    public void setEventEnd(LocalDateTime newTo) {
        eventEnd = newTo;
    }

    /**
     * {@inheritDoc}
     *
     * Returns the event's fields in a human-readable format.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeParser.dateTimeToString(eventStart)
                + " to: " + DateTimeParser.dateTimeToString(eventEnd) + ")";
    }

    /**
     * {@inheritDoc}
     *
     * Returns the event in storage format.
     */
    @Override
    public String toStorageFormat() {
        return getLogFormat("Event") + " | " + DateTimeParser.dateTimeToString(eventStart)
                + " | " + DateTimeParser.dateTimeToString(eventEnd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Event other) {
            return description.equals(other.description)
                    && eventStart.equals(other.eventStart)
                    && eventEnd.equals(other.eventEnd);
        }
        return false;
    }
}
