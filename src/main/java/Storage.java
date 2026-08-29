import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Storage {
    private static final String DATA_FILE_PATH = "data/tasks.txt";

    public Storage() {

    }

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

    public boolean doesFileExists() {
        return new File(DATA_FILE_PATH).exists();
    }

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
