package answerme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import answerme.exception.AnswerMeException;
import answerme.task.TaskList;
import answerme.task.ToDo;

public class StorageTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void saveTasks_missingParentDirectory_createsDataFile() throws AnswerMeException, IOException {
        File dataFile = temporaryDirectory.resolve("data/tasks.txt").toFile();
        Storage storage = new Storage(dataFile);
        TaskList taskList = new TaskList();
        taskList.add(new ToDo("write test"));

        storage.saveTasks(taskList);

        assertTrue(dataFile.isFile());
        assertEquals("Todo | Incomplete | write test\n", Files.readString(dataFile.toPath()));
    }

    @Test
    public void saveTasks_dataFileCannotBeCreated_throwsException() throws IOException {
        File parentFile = temporaryDirectory.resolve("parent-file").toFile();
        assertTrue(parentFile.createNewFile());

        File dataFile = new File(parentFile, "tasks.txt");
        Storage storage = new Storage(new File(parentFile, "tasks.txt"));

        AnswerMeException exception = assertThrows(AnswerMeException.class, () ->
                storage.saveTasks(new TaskList()));

        assertEquals(
                "Oh no! File: " + dataFile + " cannot be created!",
                exception.getMessage()
        );
    }

    @Test
    public void saveTasks_parentDirectoryCannotBeCreated_throwsException() throws IOException {
        File parentFile = temporaryDirectory.resolve("parent-file").toFile();
        assertTrue(parentFile.createNewFile());

        File parentDirectory = new File(parentFile, "data");
        File dataFile = new File(parentDirectory, "tasks.txt");
        Storage storage = new Storage(dataFile);

        AnswerMeException exception = assertThrows(AnswerMeException.class, () ->
                storage.saveTasks(new TaskList()));

        assertEquals(
                "Oh no! Unable to create data directory.",
                exception.getMessage()
        );
    }

    @Test
    public void loadTasks_eventStartAfterEnd_throwsException() throws IOException {
        File dataFile = temporaryDirectory.resolve("tasks.txt").toFile();
        Files.writeString(dataFile.toPath(),
                "Event | Incomplete | project meeting | 2/9/2026 17:00 | 1/9/2026 02:00\n");
        Storage storage = new Storage(dataFile);

        AnswerMeException exception = assertThrows(AnswerMeException.class, storage::loadTasks);

        assertEquals(
                "Oh no! Event start cannot occur after its end on line 1.",
                exception.getMessage()
        );
    }
}
