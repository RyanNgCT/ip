import java.util.ArrayList;
import java.util.Collection;

public class TaskList extends ArrayList<Task>{
    public TaskList() {
        super();
    }

    public TaskList(Collection<Task> collection) {
        super(collection);
    }

    public Task getTask(int taskIndex)
            throws AnswerMeException {
        if (this.size() == 0) {
            throw new AnswerMeException("The task list is empty so cannot perform operation.");
        }

        if (taskIndex < 0 || taskIndex >= size()) {
            throw new AnswerMeException("The task does not exist in the list.");
        }
        return this.get(taskIndex);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.size(); i++) {
            result += "\t" + (i + 1) + ". " + this.get(i) + "\n";
        }
        return result;
    }
}
