package answerme.exception;

public class AnswerMeException extends Exception {
    public AnswerMeException(String message) {
        super("Oh no! " + message);
    }
}
