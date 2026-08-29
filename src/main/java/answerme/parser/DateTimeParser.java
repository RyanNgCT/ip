package answerme.parser;

import answerme.exception.AnswerMeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateTimeParser {
    private static final DateTimeFormatter DATETIME_OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
    private static final DateTimeFormatter DATE_OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy");
    private final List<DateTimeFormatter> dateTimeFormats;

    public DateTimeParser() {
        // allowed list of formats
        this.dateTimeFormats = List.of(DateTimeFormatter.ofPattern("yyyy-M-d"),
                DateTimeFormatter.ofPattern("yyyy/M/d"),
                DateTimeFormatter.ofPattern("d-M-yyyy"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("MMM d yyyy"),
                DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"),
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("yyyy-M-d HH:mm"),
                DateTimeFormatter.ofPattern("yyyy/M/d HH:mm"),
                DateTimeFormatter.ofPattern("d-M-yyyy HH:mm"),
                DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"));
    }

    public LocalDateTime parse(String toParse)
            throws DateTimeParseException {
        String toParseTrimmed = toParse.trim();
        for (DateTimeFormatter format : dateTimeFormats) {
            try {
                return LocalDateTime.parse(toParseTrimmed, format);
            } catch (DateTimeParseException e) {
                // move on to the next format
            }

            try {
                return LocalDate.parse(toParseTrimmed, format).atStartOfDay();
            } catch (DateTimeParseException e) {
                // move on to the next format
            }
        }
        throw new DateTimeParseException("Invalid datetime format!", "", 0);
    }

    public LocalDateTime parseDateTime(String input)
            throws AnswerMeException {
        try {
            return this.parse(input);
        } catch (DateTimeParseException e) {
            throw new AnswerMeException("Ensure date/time is formatted correctly.");
        }
    }

    public static String dtToString(LocalDateTime toUpdate) {
        if (toUpdate == null) {
            throw new DateTimeParseException("Datetime cannot be null.", "", 0);
        }
        if (toUpdate.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return toUpdate.format(DateTimeParser.DATE_OUTPUT_FORMAT);
        }
        return toUpdate.format(DateTimeParser.DATETIME_OUTPUT_FORMAT);
    }
}
