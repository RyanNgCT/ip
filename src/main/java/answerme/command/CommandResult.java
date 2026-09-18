package answerme.command;

/**
 * Represents the result produced after processing a user command.
 *
 * @param response The message to display to the user.
 * @param responseType The visual category of the response.
 * @param shouldExit Whether the application should exit after
 *                   displaying the response.
 */
public record CommandResult(String response, ResponseType responseType, boolean shouldExit) {
}
