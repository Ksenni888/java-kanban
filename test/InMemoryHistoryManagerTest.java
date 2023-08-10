package test;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.hierarchical.Node;
import services.InMemoryTaskManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InMemoryHistoryManagerTest extends InMemoryTaskManager {


@Test
    public void addStandart(){
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
    subtask9.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
    subtask9.setDuration(10);
    InMemoryTaskManager.subtaskRepository.save(subtask9);
    InMemoryTaskManager.allTasks.add(subtask9);
    changeStatusEpic(subtask9.getEpicID());

    System.out.println("Вывод эпика по заданному id: " + getEpicById(1));
    System.out.println("Вывод подзадачи по заданному id: " + getSubtaskById(3));

    ArrayList<Task> expected = new ArrayList<>();
    expected.add(epicRepository.get(1)); expected.add(subtaskRepository.get(3));

    assertEquals(getInMemoryHistoryManager().getHistory(), expected);

}
    @Test
    public void addEmptyHistory(){
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
        changeStatusEpic(subtask9.getEpicID());

        ArrayList<Task> expected = new ArrayList<>();
        assertEquals(getInMemoryHistoryManager().getHistory(), expected);

    }

@Test
    public void addDouble(){
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
        subtask9.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
        subtask9.setDuration(10);
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        changeStatusEpic(subtask9.getEpicID());

        System.out.println("Вывод эпика по заданному id: " + getEpicById(1));
        System.out.println("Вывод эпика по заданному id: " + getEpicById(1));

        ArrayList<Task> expected = new ArrayList<>();
        expected.add(testEpic);


        assertEquals(getInMemoryHistoryManager().getHistory(), expected);}

@Test
    public void removeLastTask(){
    Epic testEpic = new Epic();
    testEpic.setId(1);
    testEpic.setName("Сварить пельмени");
    testEpic.setDescription("Рецепт пельменей");
    testEpic.setStatus(Status.NEW);
    epicRepository.save(testEpic);
    InMemoryTaskManager.allTasks.add(testEpic);

    Subtask subtask9 = new Subtask();
    subtask9.setId(2);
    testEpic.setIdSubtask(subtask9.getId());
    subtask9.setName("Вымыть полы");
    subtask9.setDescription("Вымыть полы");
    subtask9.setStatus(Status.IN_PROGRESS);
    subtask9.setEpicID(testEpic.getId());
    subtask9.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
    subtask9.setDuration(10);
    InMemoryTaskManager.subtaskRepository.save(subtask9);
    InMemoryTaskManager.allTasks.add(subtask9);
    changeStatusEpic(subtask9.getEpicID());

    Task task6 = new Task()
            .setId(3)
            .setName("Отвести ребенка в садик")
            .setDescription("Отвести ребенка в садик")
            .setStatus(models.Status.IN_PROGRESS);
    task6.setDuration(2);
    task6.setStartTime(LocalDateTime.of(2020, 1, 1, 8, 0, 0, 0));
    InMemoryTaskManager.tasksRepository.save(task6);
    InMemoryTaskManager.allTasks.add(task6);


    System.out.println("Вывод подзадачи по заданному id: " + getEpicById(1));
    System.out.println("Вывод эпика по заданному id: " + getSubtaskById(2));
    System.out.println("Вывод эпика по заданному id: " + getTaskById(3));

    getInMemoryHistoryManager().remove( getTaskById(3)); //удаление последней задачи в списке

    ArrayList<Task> expected = new ArrayList<>();
    expected.add(testEpic); expected.add(subtask9);
    assertEquals(getInMemoryHistoryManager().getHistory(),expected);}


    @Test
    public void removeMiddleTask() {
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);
        allTasks.add(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(2);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        subtask9.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
        subtask9.setDuration(10);
        subtask9.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
        subtask9.setDuration(10);
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);
        changeStatusEpic(subtask9.getEpicID());

        Task task6 = new Task()
                .setId(3)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        task6.setDuration(2);
        task6.setStartTime(LocalDateTime.of(2020, 1, 1, 8, 0, 0, 0));
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);


        System.out.println("Вывод подзадачи по заданному id: " + getEpicById(1));
        System.out.println("Вывод эпика по заданному id: " + getSubtaskById(2));
        System.out.println("Вывод эпика по заданному id: " + getTaskById(3));
        // getInMemoryHistoryManager().remove(getSubtaskById(2)); //удаление задачи из середины списка
        ArrayList<Task> expected = new ArrayList<>();

          getInMemoryHistoryManager().remove(getSubtaskById(2));
         expected.add(testEpic); expected.add(task6);
         assertEquals(getInMemoryHistoryManager().getHistory(),expected);}

    @Test
    public void removeFirstTask() {
        Epic epic6 = new Epic();
        epic6.setId(1);
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

        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        task6.setDuration(2);
        task6.setStartTime(LocalDateTime.of(2020, 1, 1, 8, 0, 0, 0));
        task6.setDuration(60);
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);


        System.out.println("Вывод подзадачи по заданному id: " + getEpicById(1));
        System.out.println("Вывод эпика по заданному id: " + getSubtaskById(3));
        System.out.println("Вывод эпика по заданному id: " + getTaskById(1));
        // getInMemoryHistoryManager().remove(getSubtaskById(2)); //удаление задачи из середины списка
        ArrayList<Task> expected = new ArrayList<>();

        getInMemoryHistoryManager().remove(getEpicById(1));
        expected.add(subtask9); expected.add(task6);
        assertEquals(getInMemoryHistoryManager().getHistory(),expected);
        getInMemoryHistoryManager().getHistory().clear();
}


}
