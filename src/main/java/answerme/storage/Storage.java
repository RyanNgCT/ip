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

public class Storage {
    private static final String DATA_FILE_PATH = "data/tasks.txt";

    public Storage() {

    }

    public void saveTasks(TaskList taskList) {
        if (!hasDataFile()) {
            try {
                createDataFile();
            } catch (AnswerMeException exception) {
                // TODO: modify behavior later
                System.out.println(exception.getMessage());
            }
        }
        try {
            FileWriter writer = new FileWriter(DATA_FILE_PATH);
            for (Task task : taskList) {
                writer.write(task.toStorageFormat() + "\n");
            }
            writer.close();
        } catch (IOException exception) {
            System.out.println("Error opening the file.");
        }
    }

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
        } catch (FileNotFoundException exception) {
            // Returns an empty task list when the data file is missing.
            return taskList;
        }
        return taskList;
    }

    public boolean hasDataFile() {
        return new File(DATA_FILE_PATH).exists();
    }

    public void createDataFile() throws AnswerMeException {
        File dataFile = new File(DATA_FILE_PATH);
        File parentDirectory = dataFile.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }
        try {
            dataFile.createNewFile();
        } catch (IOException exception) {
            throw new AnswerMeException("File: " + Storage.DATA_FILE_PATH + " cannot be created!");
        }
    }

    public Task parseTask(String line, int lineNumber) throws AnswerMeException {
        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3) {
            throw new AnswerMeException("Invalid task found on line " + lineNumber);
        }
        String type = fields[0].toLowerCase();
        String status = fields[1].toLowerCase();
        String description = fields[2];

        if (!status.equals("complete") && !status.equals("incomplete")) {
            throw new AnswerMeException("Status is malformed");
        }

        Task task = new Task(description);
        DateTimeParser dateTimeParser = new DateTimeParser();
        switch (type) {
            case "todo":
                if (fields.length != 3) {
                    throw new AnswerMeException("Invalid ToDo on line " + lineNumber);
                }
                task = new ToDo(description);
                break;

            case "deadline":
                if (fields.length != 4) {
                    throw new AnswerMeException("Invalid Deadline on line " + lineNumber);
                }
                task = new Deadline(description, dateTimeParser.parseDateTime(fields[3]));
                break;

            case "event":
                if (fields.length != 5) {
                    throw new AnswerMeException("Invalid Event on line " + lineNumber);
                }
                task = new Event(description, dateTimeParser.parseDateTime(fields[3]),
                        dateTimeParser.parseDateTime(fields[4]));
                break;

            default:
                throw new AnswerMeException("Unknown Task type on line " + lineNumber);
        }
        if (status.equals("complete")) {
            task.setComplete();
        }
        return task;
    }
}
