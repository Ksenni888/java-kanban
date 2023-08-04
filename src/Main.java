import repositories.FileBackedTasksManager;
import repositories.Managers;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        //!!!!!!Сейчас тут только происходит загрузка данных из файла через fileBackedTasksManager!!!!!
        FileBackedTasksManager fileBackedTasksManager = (FileBackedTasksManager) Managers.getDefault();
        File file = new File("file.csv");
        fileBackedTasksManager.loadFromFile(file);

        //  fileBackedTasksManager.changeStatusTask(10, Status.NEW);
        // fileBackedTasksManager.changeStatusTask(3, Status.DONE);
        //    fileBackedTasksManager.changeStatusSubtask(3, Status.DONE);
        //  System.out.println(fileBackedTasksManager.getAllTasks2());
        //    System.out.println(fileBackedTasksManager.getAllSubtasks());
        //    System.out.println(fileBackedTasksManager.getAllEpics());
       /*Код ниже может пригодиться:
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

        System.out.println("Вывод эпика по заданному" + manager.getEpicById(12));
        System.out.println("Вывод эпика по заданному" + manager.getEpicById(9));
        System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById(10));

      FileBackedTasksManager fileBackedTasksManager = (FileBackedTasksManager) Managers.getDefault();
      fileBackedTasksManager.save();

        // Это вывод по условиям из предыдущих заданий спринтов(сейчас не актуально, возможно, пригодится):
        // System.out.println(fileBackedTasksManager.getInMemoryHistoryManager().getHis2()); //вывод полностью задач из истории просмотра
        //manager.printAllTask(); //проверка вывода всех задач
        //   manager.printListSubtaskIdEpic (2); //  список всех подзадач эпика manager.printListSubtaskIdEpic (2);
        //  System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById()); //эпик 11
        //  manager.removeEpicById(11);
   */
        fileBackedTasksManager.getPrioritizedTasks();
        fileBackedTasksManager.validator();
    }
}
