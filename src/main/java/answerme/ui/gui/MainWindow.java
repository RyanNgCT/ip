package answerme.ui.gui;

import answerme.AnswerMe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
        String response = answerMe.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAnswerMeDialog(response, botImage)
        );
        userInput.clear();
    }
}

