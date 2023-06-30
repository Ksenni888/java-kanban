import models.Epic;
import models.Subtask;
import models.Task;
import repositories.Managers;
import services.InMemoryHistoryManager;
import services.InMemoryTaskManager;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        InMemoryHistoryManager historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
        Managers managers = new Managers();
        manager.getAllTasks();
        //   manager.printAllTask();

        Task task = new Task() //задаем новую задачу вместо задачи с id = 0
                .setId(0)
                .setName("Позвонить другу")
                .setDescription("")
                .setStatus(models.Status.IN_PROGRESS);
        manager.saveTask(task);


        Epic epic2 = new Epic(); //задаем новый эпик вместо эпика с id=6
        epic2.setId(6);
        epic2.setName("Погулять с кошкой");
        epic2.setDescription("Прогулка");
        epic2.setStatus(models.Status.NEW);
        manager.saveEpic(epic2);

        Subtask subtask = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask.setId(3);
        subtask.setName("Налить воды в кастрюлю");
        subtask.setDescription("");
        subtask.setStatus(models.Status.DONE);
        subtask.setEpicID(2);
        manager.saveSubtask(subtask);

        // проверка вывода всех задач manager.printAllTask();
        //  список всех подзадач эпика manager.printListSubtaskIdEpic (2);
        // поменять статус задачи  manager.changeStatusTask(0, models.Status.DONE);


        System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById(14)); //эпик 11
        System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById(12)); //эпик 11
        System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById(13)); //эпик 11
        System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById(12)); //эпик 11

        manager.removeEpicById(11);
        System.out.println("Вывод эпика по заданному" + manager.getEpicById(15));
        System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById(14));
        manager.removeEpicById(11);
        System.out.println("Вывод эпика по заданному" + manager.getEpicById(15));

    }
}

