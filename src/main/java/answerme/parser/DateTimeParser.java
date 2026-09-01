package answerme.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import answerme.exception.AnswerMeException;

/**
 * Parses date and time strings into datetime values.
 * Formats datetime values for display.
 */
public class DateTimeParser {
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy");
    private final List<DateTimeFormatter> dateTimeFormats;

    /**
     * Constructs a parser that details the application's accepted date and
     * time formats.
     */
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

    /**
     * Parses a raw date/datetime string into a {@code LocalDateTime} object.
     * A string without a specified time is interpreted as the start of the day.
     *
     * @param toParse The date or datetime string to parse.
     * @return The parsed datetime value.
     * @throws AnswerMeException If the input does not match a supported date or time format.
     */
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

    /**
     * Formats a datetime value into the application's display format.
     *
     * @param dateTime The datetime value to format.
     * @return The formatted date or datetime string.
     * @throws DateTimeParseException If the datetime value is {@code null}.
     */
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
