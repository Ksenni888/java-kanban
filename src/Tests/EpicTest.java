package Tests;

import models.Epic;
import models.Status;
import models.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repositories.EpicRepository;
import repositories.SubtaskRepository;
import services.InMemoryTaskManager;


class EpicTest extends InMemoryTaskManager {

    @Test
    public void getStatusEpicIfNoneSubtasks() { //эпик с пустым списком подзадач
        String expectedEpicStatus = "IN_PROGRESS";
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(models.Status.IN_PROGRESS);
        epicRepository.save(testEpic);
        Assertions.assertEquals(((epicRepository.getEpics()).get(1).getStatus()).toString(), expectedEpicStatus);
    }

    @Test
    public void getStatusEpicIfAllSubtasksNew() {
        Status expectedEpicStatus = Status.NEW;
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

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        inMemoryTaskManager.changeStatusEpic(1);
        Assertions.assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedEpicStatus);
    }

    @Test
    public void getStatusEpicIfAllSubtasksDONE() {
        Status expectedEpicStatus = Status.DONE;
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
        subtask9.setStatus(models.Status.DONE);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        Subtask subtask10 = new Subtask();
        subtask10.setId(4);
        testEpic.setIdSubtask(subtask10.getId());
        subtask10.setName("Протереть пыль");
        subtask10.setDescription("Протереть пыль");
        subtask10.setStatus(models.Status.DONE);
        subtask10.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask10);
        InMemoryTaskManager.allTasks.add(subtask10);

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        inMemoryTaskManager.changeStatusEpic(1);
        Assertions.assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedEpicStatus);
    }

    @Test
    public void getStatusEpicIfAllSubtasksNewAndDone() {
        Status expectedEpicStatus = Status.IN_PROGRESS;
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
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
        subtask10.setStatus(Status.DONE);
        subtask10.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask10);
        InMemoryTaskManager.allTasks.add(subtask10);

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        inMemoryTaskManager.changeStatusEpic(1);
        Assertions.assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedEpicStatus);
    }

    @Test
    public void getStatusEpicIfAllSubtasksInProgress() {
        Status expectedEpicStatus = Status.IN_PROGRESS;
        Epic testEpic = new Epic();
        testEpic.setId(1);
        testEpic.setName("Сварить пельмени");
        testEpic.setDescription("Рецепт пельменей");
        testEpic.setStatus(Status.NEW);
        epicRepository.save(testEpic);

        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        testEpic.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(models.Status.IN_PROGRESS);
        subtask9.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        Subtask subtask10 = new Subtask();
        subtask10.setId(4);
        testEpic.setIdSubtask(subtask10.getId());
        subtask10.setName("Протереть пыль");
        subtask10.setDescription("Протереть пыль");
        subtask10.setStatus(models.Status.IN_PROGRESS);
        subtask10.setEpicID(testEpic.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask10);
        InMemoryTaskManager.allTasks.add(subtask10);

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        inMemoryTaskManager.changeStatusEpic(1);
        Assertions.assertEquals(epicRepository.getEpics().get(1).getStatus(), expectedEpicStatus);
    }

}
