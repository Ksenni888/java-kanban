package Tests;

import models.Epic;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Test;
import repositories.FileBackedTasksManager;
import services.InMemoryTaskManager;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static repositories.FileBackedTasksManager.loadFromFile;
import static services.InMemoryHistoryManager.historyHash;

class FileBackedTasksManagerTest extends InMemoryTaskManager {
    private models.Enum Enum;
    private final File file = new File("file.csv");
    private final FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

    @Test
    public void NoTasks() {
        deleteAllEpics();
        deleteAllTask();
        fileBackedTasksManager.save();
        loadFromFile(file);
        ArrayList<Task> expectedTask = new ArrayList<>();
        ArrayList<Subtask> expectedSubtask = new ArrayList<>();
        ArrayList<Epic> expectedEpics = new ArrayList<>();
        assertEquals(tasksRepository.getAll(), expectedTask);
        assertEquals(subtaskRepository.getAll(), expectedSubtask);
        assertEquals(epicRepository.getAll(), expectedEpics);
    }

    @Test
    public void NoHistory() {
        historyHash.clear();
        deleteAllEpics();
        deleteAllTask();

        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        task6.setStartTime(LocalDateTime.of(2020, 1, 1, 8, 0, 0, 0));
        task6.setDuration(60);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);

        Epic epic6 = new Epic();
        epic6.setId(2);
        epic6.setName("Сделать уборку");
        epic6.setDescription("Сделать уборку");
        epic6.setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.epicRepository.save(epic6);
        InMemoryTaskManager.allTasks.add(epic6);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        epic6.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(models.Status.NEW);
        subtask9.setEpicID(epic6.getId());
        subtask9.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
        subtask9.setDuration(10);
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        //  getInMemoryHistoryManager().getHistoryHash().clear();
        fileBackedTasksManager.save();
        loadFromFile(file);
        ArrayList<Task> expectedTask = new ArrayList<>();
        expectedTask.add(task6);
        ArrayList<Subtask> expectedSubtask = new ArrayList<>();
        expectedSubtask.add(subtask9);
        ArrayList<Epic> expectedEpics = new ArrayList<>();
        expectedEpics.add(epic6);
        assertEquals(tasksRepository.getAll().toString(), expectedTask.toString());
        assertEquals(subtaskRepository.getAll().toString(), expectedSubtask.toString());
        assertEquals(epicRepository.getAll().toString(), expectedEpics.toString());
        deleteAllEpics();
        deleteAllTask();
    }

    @Test
    public void EpicWhithoutSubtasks() {
        deleteAllEpics();
        deleteAllTask();
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        task6.setDuration(2);
        // task6.setStartTime(LocalDateTime.of(2020, 1, 1, 8, 0, 0, 0));
        task6.setStartTime(null);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);

        Epic epic6 = new Epic();
        epic6.setId(2);
        epic6.setName("Сделать уборку");
        epic6.setDescription("Сделать уборку");
        epic6.setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.epicRepository.save(epic6);
        InMemoryTaskManager.allTasks.add(epic6);

        System.out.println("Вывод эпика по заданному id: " + getEpicById(2));
        fileBackedTasksManager.save();
        loadFromFile(file);
        ArrayList<Task> expectedTask = new ArrayList<>();
        expectedTask.add(task6);
        ArrayList<Epic> expectedEpics = new ArrayList<>();
        expectedEpics.add(epic6);
        assertEquals(tasksRepository.getAll().toString(), expectedTask.toString());
        assertEquals(epicRepository.getAll().toString(), expectedEpics.toString());
        deleteAllEpics();
        deleteAllTask();
    }


}