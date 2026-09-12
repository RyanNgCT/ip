package answerme.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import answerme.exception.AnswerMeException;
import answerme.parser.DateTimeParser;
import answerme.task.Deadline;
import answerme.task.Event;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.task.ToDo;

/**
 * Represents a Storage Manager for persisting tasks in a text file
 * on disk.
 */
public class Storage {
    private static final String DEFAULT_DATA_FILE_PATH = "data/tasks.txt";
    private static final int PRESERVE_EMPTY_FIELDS = -1;
    private static final int TYPE_FIELD_INDEX = 0;
    private static final int STATUS_FIELD_INDEX = 1;
    private static final int DESCRIPTION_FIELD_INDEX = 2;
    private static final int FIRST_DATE_FIELD_INDEX = 3;
    private static final int SECOND_DATE_FIELD_INDEX = 4;

    private static final int MINIMUM_FIELD_COUNT = 3;
    private static final int TODO_FIELD_COUNT = 3;
    private static final int DEADLINE_FIELD_COUNT = 4;
    private static final int EVENT_FIELD_COUNT = 5;

    private enum TaskType {
        TODO("ToDo", TODO_FIELD_COUNT),
        DEADLINE("Deadline", DEADLINE_FIELD_COUNT),
        EVENT("Event", EVENT_FIELD_COUNT);

        private final String displayName;
        private final int expectedFieldCount;

        TaskType(String displayName, int expectedFieldCount) {
            this.displayName = displayName;
            this.expectedFieldCount = expectedFieldCount;
        }
    }

    private enum TaskStatus {
        COMPLETE,
        INCOMPLETE
    }

    private final File dataFile;

    /**
     * Constructs a new Storage Manager.
     */
    public Storage() {
        this(new File(DEFAULT_DATA_FILE_PATH));
    }

    /**
     * Constructs a storage manager that uses the specified data file.
     *
     * @param dataFile The file used to load and save tasks.
     */
    public Storage(File dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * Saves tasks in the configured data file.
     *
     * @param taskList The list of tasks to be saved in the output file.
     * @throws AnswerMeException If the data file cannot be created or written.
     */
    public void saveTasks(TaskList taskList) throws AnswerMeException {
        if (!hasDataFile()) {
            createDataFile();
        }

        try (FileWriter writer = new FileWriter(dataFile)) {
            for (Task task : taskList) {
                writer.write(task.toStorageFormat() + "\n");
            }
        } catch (IOException exception) {
            throw new AnswerMeException("Unable to save tasks.");
        }
    }

    /**
     * Loads tasks from the data file into a task list.
     *
     * @return A populated {@code TaskList} containing the tasks stored in the data
     *         file, or an empty {@code TaskList} if the file does not exist.
     * @throws AnswerMeException If the data file cannot be read or contains
     *                           an invalid task.
     */
    public TaskList loadTasks() throws AnswerMeException {
        TaskList taskList = new TaskList();

        if (!hasDataFile()) {
            return taskList;
        }

        try (Scanner scanner = new Scanner(dataFile)) {
            int lineNumber = 1;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (!line.isBlank()) {
                    taskList.add(parseTask(line, lineNumber));
                }
                lineNumber++;
            }
        } catch (FileNotFoundException exception) {
            throw new AnswerMeException("Unable to read saved tasks from "
                    + dataFile + ".");
        }
        return taskList;
    }

    /**
     * Checks whether the configured data file exists.
     *
     * @return {@code true} if the file exists and {@code false} if not.
     */
    public boolean hasDataFile() {
        return dataFile.exists();
    }

    /**
     * Creates the task data file and any missing parent directory if they
     * do not exist.
     *
     * @throws AnswerMeException If the data file cannot be created.
     */
    public void createDataFile() throws AnswerMeException {
        File parentDirectory = dataFile.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }
        try {
            dataFile.createNewFile();
        } catch (IOException exception) {
            throw new AnswerMeException("File: " + dataFile + " cannot be created!");
        }
    }

    /**
     * Converts individual lines into corresponding {@code Task} objects.
     *
     * @param line The serialized task data to parse.
     * @param lineNumber The current line number being parsed, used in error messages.
     * @return The task represented by the given line.
     * @throws AnswerMeException If the task has an invalid format, status or type.
     */
    public Task parseTask(String line, int lineNumber) throws AnswerMeException {
        String[] fields = parseFields(line, lineNumber);
        TaskType taskType = parseTaskType(fields[TYPE_FIELD_INDEX], lineNumber);
        TaskStatus taskStatus = parseTaskStatus(fields[STATUS_FIELD_INDEX]);
        Task task = createTask(taskType, fields, lineNumber);

        if (taskStatus == TaskStatus.COMPLETE) {
            task.setComplete();
        }
        return task;
    }

    private String[] parseFields(String line, int lineNumber) throws AnswerMeException {
        String[] fields = line.split("\\s*\\|\\s*", PRESERVE_EMPTY_FIELDS);
        if (fields.length < MINIMUM_FIELD_COUNT) {
            throw new AnswerMeException("Invalid task found on line " + lineNumber);
        }
        return fields;
    }

    private TaskType parseTaskType(String type, int lineNumber) throws AnswerMeException {
        try {
            return TaskType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new AnswerMeException("Unknown task type on line " + lineNumber);
        }
    }

    private TaskStatus parseTaskStatus(String value) throws AnswerMeException {
        try {
            return TaskStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new AnswerMeException("Status is malformed");
        }
    }

    private void checkFieldCount(TaskType taskType, String[] fields, int lineNumber)
            throws AnswerMeException {
        if (fields.length != taskType.expectedFieldCount) {
            throw new AnswerMeException("Invalid " + taskType.displayName
                    + " on line " + lineNumber);
        }
    }

    private Task createTask(TaskType taskType, String[] fields, int lineNumber)
            throws AnswerMeException {
        checkFieldCount(taskType, fields, lineNumber);
        String taskDescription = fields[DESCRIPTION_FIELD_INDEX];

        DateTimeParser dateTimeParser = new DateTimeParser();
        switch (taskType) {
            case TODO:
                return new ToDo(taskDescription);

            case DEADLINE:
                return new Deadline(taskDescription,
                        dateTimeParser.parseDateTime(fields[FIRST_DATE_FIELD_INDEX]));

            case EVENT:
                return new Event(
                        taskDescription,
                        dateTimeParser.parseDateTime(fields[FIRST_DATE_FIELD_INDEX]),
                        dateTimeParser.parseDateTime(fields[SECOND_DATE_FIELD_INDEX]));

            default:
                throw new AnswerMeException("Unknown Task type " + taskType
                        + " on line " + lineNumber);
        }
    }
}
