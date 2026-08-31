package answerme.task;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toStorageFormat() {
        return getLogFormat("Todo");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof ToDo other) {
            return description.equals(other.description);
        }
        return true;
    }
}
