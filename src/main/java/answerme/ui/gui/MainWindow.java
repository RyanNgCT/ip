package answerme.ui.gui;

import answerme.AnswerMe;
import answerme.command.CommandResult;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controls user interactions in the primary AnswerMe window.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private AnswerMe answerMe;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/AnswerMe.jpg"));

    /**
     * Configures the scroll pane to follow the newest dialog box.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Supplies the AnswerMe instance that handles user commands.
     *
     * @param bot the AnswerMe instance to use.
     */
    public void setAnswerMe(AnswerMe bot) {
        answerMe = bot;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * AnswerMe's reply and then appends them to the dialog container. Clears
     * the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        CommandResult result = answerMe.processCommand(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAnswerMeDialog(result.response(), botImage)
        );
        userInput.clear();

        if (result.shouldExit()) {
            Stage stage = (Stage) userInput.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Displays the chatbot's welcome message and if task loading failed,
     * its loading error message in the dialog container.
     */
    public void showWelcome() {
        String loadingErrorMessage = answerMe.getLoadingErrorMessage();

        dialogContainer.getChildren().addAll(
                DialogBox.getAnswerMeDialog(answerMe.showWelcomeMessage(), botImage)
        );

        if (loadingErrorMessage != null) {
            dialogContainer.getChildren().add(
                    DialogBox.getAnswerMeDialog(loadingErrorMessage, botImage)
            );
        }
    }
}

