public class MarkCommand extends Command {
    private final int taskIndex;

    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException {
        Task t = taskList.get(taskIndex);
        t.setComplete();
        ui.showMessage("Nice! I have marked this task as done:\n" + t);
        storage.saveTasks(taskList);
    }
}
