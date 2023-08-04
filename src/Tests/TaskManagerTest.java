package Tests;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.*;
import services.InMemoryTaskManager;
import services.TaskManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskManagerTest<T extends TaskManager> extends InMemoryTaskManager {
    private final InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    public void printListSubtaskIdEpicStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(models.Status.IN_PROGRESS);
        epicRepository.save(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(models.Status.NEW);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        Subtask subtask10 = new Subtask();
        subtask10.setId(4);
        testEpic.setIdSubtask(subtask10.getId());
        subtask10.setName("Протереть пыль");
        subtask10.setDescription("Протереть пыль");
        subtask10.setStatus(models.Status.NEW);
        subtask10.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask10);
        InMemoryTaskManager.allTasks.add(subtask10);

        ArrayList<Subtask> exp = new ArrayList<>();
        exp.add(subtask9);
        exp.add(subtask10);
        Status expectedEpicStatus = Status.NEW;

        Assertions.assertEquals(getListSubtask(1), exp); //проверка листа подзадач
        inMemoryTaskManager.changeStatusEpic(1);
        Assertions.assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedEpicStatus); //проверка статуса эпика
    }

    @Test
    public void printListSubtaskNull() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.IN_PROGRESS);
        epicRepository.save(testEpic);
        deleteAllSubtask();

        Integer exp = 0;
        Status expectedEpicStatus = Status.IN_PROGRESS;

        Assertions.assertEquals(getListSubtask(1).size(), exp); //проверка листа подзадач
        Assertions.assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedEpicStatus); //проверка статуса эпика
    }

    @Test
    public void printListSubtaskWhithNonExistentID() {
        ArrayList<Subtask> exp = new ArrayList<>();
        Assertions.assertEquals(getListSubtask(10), exp);
    }


    @Test
    public void printAllTaskWithNull() {
        ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));
        deleteAllSubtask();
        deleteAllTasks();
        deleteAllEpics();
        printAllTask();
        String expectedOutput1 = "id,type,name,status,description,epic\r\n";
        assertEquals(expectedOutput1, outContent1.toString());
    }

    @Test
    public void printAllTaskStandart() {

        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);


        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(models.Status.NEW);
        epicRepository.save(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(models.Status.NEW);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        printAllTask();
        String expectedOutput = "id,type,name,status,description,epic\r\n" +
                "1,TASK,Отвести ребенка в садик,IN_PROGRESS,Отвести ребенка в садик\r\n" +
                "1,EPIC,Сварить пельмени,NEW,Рецепт пельменей\r\n" +
                "3,SUBTASK,Вымыть полы,NEW,Вымыть полы,1\r\n";
        assertEquals(expectedOutput, outContent.toString());

    }


    @Test
    public void deleteAllTasksStandart() {
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);


        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(models.Status.IN_PROGRESS);
        epicRepository.save(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(models.Status.NEW);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        ArrayList<Task> expected = new ArrayList<>();

        allTasks.clear();
        assertEquals(allTasks, expected);


    }

    @Test
    public void deleteAllTasksNull() {
        ArrayList<Task> expected = new ArrayList<>();
        allTasks.clear();
        assertEquals(allTasks, expected);

    }

    @Test
    public void deleteAllTaskStandart() {
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);

        HashMap<Integer, Task> expected = new HashMap<>();
        tasksRepository.deleteAll();

        assertEquals(tasksRepository.getTasks(), expected);

    }

    @Test
    public void deleteAllTaskNull() {
        HashMap<Integer, Task> expected = new HashMap<>();
        tasksRepository.deleteAll();
        assertEquals(tasksRepository.getTasks(), expected);

    }

    @Test
    public void deleteAllEpicsStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(models.Status.IN_PROGRESS);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        epicRepository.deleteAll();
        HashMap<Integer, Epic> expected = new HashMap<>();
        assertEquals(epicRepository.getEpics(), expected);
    }

    @Test
    public void deleteAllEpicsNull() {
        epicRepository.deleteAll();
        HashMap<Integer, Epic> expected = new HashMap<>();
        assertEquals(epicRepository.getEpics(), expected);

    }

    @Test
    public void deleteAllSubtaskStandartChekEpicStatus() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        inMemoryTaskManager.changeStatusEpic(1); //in progress

        inMemoryTaskManager.deleteAllSubtask();

        HashMap<Integer, Subtask> expected = new HashMap<>();
        assertEquals(subtaskRepository.getSubtasks(), expected);
        Status expectedStatus = Status.IN_PROGRESS;
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedStatus);
    }

    @Test
    public void deleteAllSubtaskNull() {
        inMemoryTaskManager.deleteAllSubtask();
        HashMap<Integer, Subtask> expected = new HashMap<>();
        assertEquals(subtaskRepository.getSubtasks(), expected);

    }

    @Test
    public void saveTaskStandart() {

        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);

        Task expected = task6;
        assertEquals(tasksRepository.getTasks().get(1), expected);
    }

    @Test
    public void saveSubtaskStandartCheckEpicId() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        Subtask expected = subtask9;
        Integer expectedEpicId = 1;
        assertEquals(subtaskRepository.getSubtasks().get(3), expected);
        assertEquals(subtaskRepository.getSubtasks().get(3).getEpicID(), expectedEpicId);
    }

    @Test
    public void saveEpicStandartCheckStatus() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        Epic expected = testEpic;
        Status expectedEpicStatus = Status.IN_PROGRESS;
        assertEquals(epicRepository.getEpics().get(1), expected);
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedEpicStatus);
    }

    @Test
    public void removeTaskByIdStandart() {
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);

        removeTaskById(1);

        HashMap<Integer, Task> expected = new HashMap<>();
        assertEquals(tasksRepository.getTasks(), expected);
    }

    @Test
    public void removeNotExistingIDTask() {
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);

        removeTaskById(2);
        HashMap<Integer, Task> expected = new HashMap<>();
        expected.put(1, task6);
        assertEquals(tasksRepository.getTasks(), expected);
    }

    @Test
    public void removeNotExistingIDTaskWhenHashMapTasksEmpty() {

        Integer expected = tasksRepository.getTasks().size();
        removeTaskById(2);
        assertEquals(tasksRepository.getTasks().size(), expected);

    }


    @Test
    public void removeEpicByIdStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);
        removeEpicById(1);
        HashMap<Integer, Epic> expected = new HashMap<>();
        assertEquals(epicRepository.getEpics(), expected);
    }

    @Test
    public void removeEpicByIdWrongId() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        removeEpicById(2);

        HashMap<Integer, Epic> expected = new HashMap<>();
        expected.put(1, testEpic);
        assertEquals(epicRepository.getEpics(), expected);
    }

    @Test
    public void removeSubtaskByIdStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        removeSubtaskById(3);

        HashMap<Integer, Subtask> expected = new HashMap<>();
        assertEquals(subtaskRepository.getSubtasks(), expected);
        Status expectedStatus = Status.IN_PROGRESS;
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedStatus);

    }

    @Test
    public void removeSubtaskByWrongId() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        removeSubtaskById(2);
        HashMap<Integer, Subtask> expected = new HashMap<>();
        expected.put(3, subtask9);
        assertEquals(subtaskRepository.getSubtasks(), expected);
        Status expectedStatus = Status.IN_PROGRESS;
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedStatus);

    }

    @Test
    public void removeSubtaskByWrongIdWhenHashMapSubtasksEmpty() {
        Integer expected = subtaskRepository.getSubtasks().size();
        removeSubtaskById(2);
        assertEquals(subtaskRepository.getSubtasks().size(), expected);
    }

    @Test
    public void changeStatusTaskStandart() {
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);

        changeStatusTask(1, Status.NEW);
        Status expected = Status.NEW;
        assertEquals(tasksRepository.getTasks().get(1).getStatus(), expected);
    }

    @Test
    public void changeStatusTaskWrongId() {
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);

        changeStatusTask(2, Status.NEW);
        Status expected = Status.IN_PROGRESS;
        assertEquals(tasksRepository.getTasks().get(1).getStatus(), expected);
    }

    @Test
    public void changeStatusSubtaskStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        changeStatusSubtask(3, Status.DONE);
        Status expected = Status.DONE;
        Status expectedStatusEpic = Status.IN_PROGRESS;
        assertEquals(subtaskRepository.getSubtasks().get(3).getStatus(), expected);
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedStatusEpic);

    }

    @Test
    public void changeStatusSubtaskWrongIdSubtask() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        changeStatusSubtask(2, Status.DONE);
        Status expected = Status.IN_PROGRESS;
        Status expectedStatusEpic = Status.IN_PROGRESS;
        assertEquals(subtaskRepository.getSubtasks().get(3).getStatus(), expected);
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedStatusEpic);


    }

    @Test
    public void changeStatusEpicWithEmptySubtask() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.DONE);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);
        deleteAllSubtask();

        changeStatusEpic(1);
        Status expected = Status.DONE;
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expected);

    }

    @Test
    public void changeStatusEpicStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        changeStatusEpic(1);
        Status expected = Status.IN_PROGRESS;
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expected);
    }

    @Test
    public void changeStatusEpicWrongId() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        changeStatusEpic(2);
        Status expected = Status.IN_PROGRESS;
        assertEquals(epicRepository.getEpics().get(1).getStatus(), expected);
    }


    @Test
    public void getAllTasks2Standart() {
        deleteAllTasks();
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);

        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());


        ArrayList<Task> expected = new ArrayList<>();
        expected.add(task6);
        expected.add(testEpic);
        expected.add(subtask9);
        assertEquals(allTasks, expected);
    }

    @Test
    public void getAllTasks2EmptyArrayTask() {
        deleteAllTasks();
        ArrayList<Task> expected = new ArrayList<>();
        assertEquals(allTasks, expected);

    }

    @Test
    public void getAllEpicsStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Epic testEpic1 = new Epic();
        testEpic1.setId(2);
        testEpic1.setName("Погулять с собакой");
        testEpic1.setDescription("Прогулка");
        testEpic1.setStatus(Status.NEW);
        epicRepository.save(testEpic1);
        InMemoryTaskManager.allTasks.add(testEpic1);


        ArrayList<Task> expected = new ArrayList<>();
        expected.add(testEpic);
        expected.add(testEpic1);
        assertEquals(getAllEpics(), expected);

    }

    @Test
    public void getAllEpicsEmptyHash() {
        deleteAllEpics();
        ArrayList<Task> expected = new ArrayList<>();
        assertEquals(getAllEpics(), expected);

    }

    @Test
    public void getTaskByIdStandart() {
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);


        Task expected1 = task6;
        assertEquals(getTaskById(1), expected1);
    }

    @Test
    public void getTaskByIdEmptyAndWrongId() {
        assertEquals(getTaskById(1), null);

    }

    @Test
    public void getTaskByIdWrongId() {
        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);
        assertEquals(getTaskById(2), null);

    }

    @Test
    public void getEpicByIdStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);
        Epic expected = testEpic;
        assertEquals(getEpicById(1), expected);
    }

    @Test
    public void getEpicByIdEmptyHash() {
        deleteAllEpics();
        assertEquals(getEpicById(1), null);
    }

    @Test
    public void getEpicByWrongId() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);
        assertEquals(getEpicById(2), null);
    }

    @Test
    public void getSubtaskByIdStandart() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        Subtask expected = subtask9;
        assertEquals(getSubtaskById(3), expected);

    }

    @Test
    public void getSubtaskByIdEmptyHash() {
        deleteAllSubtask();
        assertEquals(getSubtaskById(3), null);
    }

    @Test
    public void getSubtaskByWrongId() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        assertEquals(getSubtaskById(4), null);

    }

    @Test
    public void getListSubtaskStandart() {

        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        Subtask subtask10 = new Subtask();
        subtask10.setId(4);
        testEpic.setIdSubtask(subtask10.getId());
        subtask10.setName("Протереть пыль");
        subtask10.setDescription("Протереть пыль");
        subtask10.setStatus(models.Status.NEW);
        subtask10.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask10);
        InMemoryTaskManager.allTasks.add(subtask10);

        ArrayList<Subtask> expected = new ArrayList<>();
        expected.add(subtask9);
        expected.add(subtask10);

        assertEquals(getListSubtask(1), expected);

    }

    @Test
    public void getListSubtaskEmptyHash() {
        deleteAllSubtask();
        ArrayList<Subtask> expected = new ArrayList<>();
        assertEquals(getListSubtask(1), expected);
    }

    @Test
    public void getListSubtaskWrongEpicId() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        InMemoryTaskManager.allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        inMemoryTaskManager.changeStatusEpic(subtask9.getEpicID());

        Subtask subtask10 = new Subtask();
        subtask10.setId(4);
        testEpic.setIdSubtask(subtask10.getId());
        subtask10.setName("Протереть пыль");
        subtask10.setDescription("Протереть пыль");
        subtask10.setStatus(models.Status.NEW);
        subtask10.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask10);
        InMemoryTaskManager.allTasks.add(subtask10);

        ArrayList<Subtask> expected = new ArrayList<>();


        assertEquals(getListSubtask(2), expected);

    }

}















