package answerme.exception;

/**
 * Represents an exception thrown when the application encounters
 * an unexpected exception.
 */
public class AnswerMeException extends Exception {
    /**
     * Constructs a new custom exception object with the specified
     * error message.
     *
     * @param message The message describing the exception.
     */
    public AnswerMeException(String message) {
        super("Oh no! " + message);
    }
}
