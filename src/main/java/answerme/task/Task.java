package answerme.task;

public class Task {
    protected String description;
    protected boolean isComplete;

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    public Task(String description, boolean isComplete) {
        this.description = description;
        this.isComplete = isComplete;
    }

    public String getStatusIcon() {
        return isComplete() ? "X" : " ";
    }

    // accessors
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public void setComplete() {
        this.isComplete = true;
    }

    public void setIncomplete() {
        this.isComplete = false;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    protected String getLogFormat(String type) {
        String status = isComplete ? "Complete" : "Incomplete";
        return type + " | " + status + " | " + description;
    }

    public String toStorageFormat() {
        return "Task";
    }
}
