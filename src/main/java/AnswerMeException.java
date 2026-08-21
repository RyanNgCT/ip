public class AnswerMeException extends IllegalArgumentException {
    public AnswerMeException(String message) {
        super("Oh no! " + message);
    }
}
