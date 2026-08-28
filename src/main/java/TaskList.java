import java.util.ArrayList;

public class TaskList extends ArrayList<Task>{
    public TaskList() {
        super();
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
