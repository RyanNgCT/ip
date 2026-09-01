package answerme.storage;

import answerme.task.ToDo;
import answerme.task.Deadline;
import answerme.task.Event;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.exception.AnswerMeException;
import answerme.parser.DateTimeParser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a Storage Manager for persisting tasks in a text file
 * on disk.
 */
public class Storage {
    private static final String DATA_FILE_PATH = "data/tasks.txt";

    /**
     * Constructs a new Storage Manager
     */
    public Storage() {

    }

    /**
     * Saves tasks in the file specified by {@code DATA_FILE_PATH}
     *
     * @param taskList The list of tasks to be saved in the output file.
     */
    public void saveTasks(TaskList taskList) {
        if (!this.doesFileExists()) {
            try {
                createDataFile();
            }
            catch (AnswerMeException e) {
                // TODO: modify behavior later
                System.out.println(e.getMessage());
            }
        }
        try {
            FileWriter writer = new FileWriter(DATA_FILE_PATH);
            for (Task t: taskList) {
                writer.write(t.toStorageFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error opening the file.");
        }
    }

    /**
     * Loads tasks from the data file into a task list.
     *
     * @return A populated {@code TaskList} containing the tasks stored in the data
     *         file, or an empty {@code TaskList} is the file does not exist.
     * @throws AnswerMeException If the parsed line cannot be converted to a valid
     *                           task.
     */
    public TaskList loadTasks() throws AnswerMeException {
        TaskList taskList = new TaskList();
        try (Scanner scanner = new Scanner(new File(DATA_FILE_PATH))) {
            int lineNumber = 1;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (!line.isBlank()) {
                    taskList.add(parseTask(line, lineNumber));
                }
                lineNumber++;
            }
        }
        catch (FileNotFoundException e) {
            // missing file -> return empty task list
            return taskList;
        }
        return taskList;
    }

    /**
     * Checks whether the data file at {@code DATA_FILE_PATH} exists.
     *
     * @return {@code true} if the file exists and {@code false} if not.
     */
    public boolean doesFileExists() {
        return new File(DATA_FILE_PATH).exists();
    }

    /**
     * Creates the task data file and any missing parent directory if they
     * do not exist.
     *
     * @throws AnswerMeException If the data file cannot be created.
     */
    public void createDataFile() throws AnswerMeException {
        File f = new File(DATA_FILE_PATH);
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        try {
            f.createNewFile();
        }
        catch (IOException e) {
            throw new AnswerMeException("File: " + Storage.DATA_FILE_PATH + " cannot be created!");
        }
    }

    /**
     * Converts individual lines into corresponding {@code Task} objects.
     *
     * @param line The serialised task data to parse.
     * @param lineNumber The current line number being parsed, used in error messages.
     * @return The task represented by the given line.
     * @throws AnswerMeException If the task has an invalid format, status or type.
     */
    public Task parseTask(String line, int lineNumber) throws AnswerMeException {
        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3) {
            throw new AnswerMeException("Invalid task found on line " + lineNumber);
        }
        String type = fields[0].toLowerCase();
        String status = fields[1].toLowerCase();
        String desc = fields[2];

        if (!status.equals("complete") && !status.equals("incomplete")) {
            throw new AnswerMeException("Status is malformed");
        }

        Task t = new Task(desc);
        DateTimeParser dtParser = new DateTimeParser();
        switch (type) {
            case "todo":
                if (fields.length != 3) {
                    throw new AnswerMeException("Invalid ToDo on line " + lineNumber);
                }
                t = new ToDo(desc);
                break;

            case "deadline":
                if (fields.length != 4) {
                    throw new AnswerMeException("Invalid Deadline on line " + lineNumber);
                }
                t = new Deadline(desc, dtParser.parseDateTime(fields[3]));
                break;

            case "event":
                if (fields.length != 5) {
                    throw new AnswerMeException("Invalid Event on line " + lineNumber);
                }
                t = new Event(desc, dtParser.parseDateTime(fields[3]), dtParser.parseDateTime(fields[4]));
                break;

            default:
                throw new AnswerMeException("Unknown Task type on line " + lineNumber);
        }
        if (status.equals("complete")) {
            t.setComplete();
        }
        return t;
    }
}
