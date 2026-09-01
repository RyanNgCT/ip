package answerme.task;

import java.time.LocalDateTime;

import answerme.parser.DateTimeParser;

public class Event extends Task {
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeParser.dateTimeToString(startTime)
                + " to: " + DateTimeParser.dateTimeToString(endTime) + ")";
    }

    @Override
    public String toStorageFormat() {
        return getLogFormat("Event") + " | " + DateTimeParser.dateTimeToString(startTime)
                + " | " + DateTimeParser.dateTimeToString(endTime);
    }

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
