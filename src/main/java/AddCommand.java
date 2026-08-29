public abstract class AddCommand extends Command {
    private final Task task;

    protected AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws AnswerMeException {
        taskList.add(task);
        ui.printAddNewItem(task, taskList.size());
        storage.saveTasks(taskList);
    }
}
