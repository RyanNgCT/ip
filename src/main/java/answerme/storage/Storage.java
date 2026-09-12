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
    private final File dataFile;

    /**
     * Constructs a new Storage Manager.
     */
    public Storage() {
        this(new File(DEFAULT_DATA_FILE_PATH));
    }

    Storage(File dataFile) {
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
     * @throws AnswerMeException If the parsed line cannot be converted to a valid
     *                           task.
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
        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3) {
            throw new AnswerMeException("Invalid task found on line " + lineNumber);
        }
        String taskType = fields[0].toLowerCase();
        String taskStatus = fields[1].toLowerCase();
        String taskDescription = fields[2];

        if (!taskStatus.equals("complete") && !taskStatus.equals("incomplete")) {
            throw new AnswerMeException("Status is malformed");
        }

        Task task = new Task(taskDescription);
        DateTimeParser dateTimeParser = new DateTimeParser();
        switch (taskType) {
            case "todo":
                if (fields.length != 3) {
                    throw new AnswerMeException("Invalid ToDo on line " + lineNumber);
                }
                task = new ToDo(taskDescription);
                break;

            case "deadline":
                if (fields.length != 4) {
                    throw new AnswerMeException("Invalid Deadline on line " + lineNumber);
                }
                task = new Deadline(taskDescription, dateTimeParser.parseDateTime(fields[3]));
                break;

            case "event":
                if (fields.length != 5) {
                    throw new AnswerMeException("Invalid Event on line " + lineNumber);
                }
                task = new Event(taskDescription, dateTimeParser.parseDateTime(fields[3]),
                        dateTimeParser.parseDateTime(fields[4]));
                break;

            default:
                throw new AnswerMeException("Unknown Task type on line " + lineNumber);
        }
        if (taskStatus.equals("complete")) {
            task.setComplete();
        }
        return task;
    }
}
