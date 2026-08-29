public class AddToDoCommand extends AddCommand {
    public AddToDoCommand(String description) {
        super(new ToDo(description));
    }
}
