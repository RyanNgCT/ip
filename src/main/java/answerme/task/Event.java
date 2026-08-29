package answerme.task;

import answerme.parser.DateTimeParser;
import java.time.LocalDateTime;

public class Event extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeParser.dtToString(from) +
                " to: " + DateTimeParser.dtToString(to) + ")";
    }

    @Override
    public String toStorageFormat() {
        return getLogFormat("Event") + " | " + DateTimeParser.dtToString(from) +
                " | " + DateTimeParser.dtToString(to);
    }
}
