public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public void setDeadLine(String newDeadline) {
        this.by = newDeadline;
    }

    public String getDueBy() {
        return this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toStorageFormat() {
        return getLogFormat("Deadline") + " | " + by;
    }
}
