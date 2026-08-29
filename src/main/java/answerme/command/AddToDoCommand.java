package answerme.command;

import answerme.task.ToDo;

public class AddToDoCommand extends AddCommand {
    public AddToDoCommand(String description) {
        super(new ToDo(description));
    }
}
