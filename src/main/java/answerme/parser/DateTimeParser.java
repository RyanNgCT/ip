package answerme.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import answerme.exception.AnswerMeException;

public class DateTimeParser {
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy");
    private final List<DateTimeFormatter> dateTimeFormats;

    public DateTimeParser() {
        // Contains allowed date and time input formats.
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

    public LocalDateTime parseDateTime(String toParse)
            throws AnswerMeException {
        String toParseTrimmed = toParse.trim();
        for (DateTimeFormatter format : dateTimeFormats) {
            try {
                return LocalDateTime.parse(toParseTrimmed, format);
            } catch (DateTimeParseException exception) {
                // Continues with the next format.
            }

            try {
                return LocalDate.parse(toParseTrimmed, format).atStartOfDay();
            } catch (DateTimeParseException exception) {
                // Continues with the next format.
            }
        }
        throw new AnswerMeException("Ensure date/time is formatted correctly.");
    }

    public static String dateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new DateTimeParseException("Datetime cannot be null.", "", 0);
        }
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.format(DateTimeParser.OUTPUT_DATE_FORMAT);
        }
        return dateTime.format(DateTimeParser.OUTPUT_DATETIME_FORMAT);
    }
}
