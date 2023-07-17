import models.Task;
import repositories.FileBackedTasksManager;
import repositories.Managers;
import services.InMemoryHistoryManager;
import services.InMemoryTaskManager;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        InMemoryTaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        InMemoryHistoryManager historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
        Managers managers = new Managers();
        manager.getAllTasks();

        Task task = new Task() //задаем новую задачу вместо задачи с id = 0
                .setId(0)
                .setName("Позвонить другу")
                .setDescription("Description")
                .setStatus(models.Status.IN_PROGRESS);
        manager.saveTask(task);

        System.out.println("Вывод эпика по заданному" + manager.getEpicById(2));
        System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById(5));


        File file = new File( "file.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.save();
        // System.out.println(fileBackedTasksManager.getInMemoryHistoryManager().getHis2()); //вывод полностью задач из истории просмотра
        //manager.printAllTask(); //проверка вывода всех задач
        //   manager.printListSubtaskIdEpic (2); //  список всех подзадач эпика manager.printListSubtaskIdEpic (2);
        //  System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById()); //эпик 11
        //  manager.removeEpicById(11);
    }
}
