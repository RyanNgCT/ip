package answerme.task;

/**
 * Represents a task that can be marked as complete or incomplete.
 */
public class Task {
    protected String description;
    protected boolean isComplete;

    /**
     * Constructs a new task with a description specified. Its default status
     * is incomplete (i.e. {@code false}).
     *
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    /**
     * Creates a task with the specified description and completion status.
     *
     * @param description The task description.
     * @param isComplete The completion status of the task.
     */
    public Task(String description, boolean isComplete) {
        this.description = description;
        this.isComplete = isComplete;
    }

    /**
     * Returns the icon representing the current task's completion status.
     *
     * @return {@code "X"} if the task is complete, otherwise a
     *                     {@code " "} character.
     */
    public String getStatusIcon() {
        return (getCompletedStatus() ? "X" : " ");
    }

    // accessors
    public void setDesc(String newDesc) {
        this.description = newDesc;
    }

    public String getDesc() {
        return this.description;
    }

    public void setComplete() {
        this.isComplete = true;
    }

    public void setIncomplete() {
        this.isComplete = false;
    }

    public boolean getCompletedStatus() {
        return this.isComplete;
    }

    /**
     * Returns this task's status icon followed by its description.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Formats the (sub)task into its storage format.
     *
     * @param type The sub-task type.
     * @return The formatted task.
     */
    protected String getLogFormat(String type) {
        String status = isComplete ? "Complete" : "Incomplete";
        return type + " | " + status + " | " + description;
    }

    /**
     * Returns this task's data in the format used for storage.
     *
     * @return The storage representation of this task.
     */
    public String toStorageFormat() {
        return "Task";
    }
}
