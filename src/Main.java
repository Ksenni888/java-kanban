import models.Epic;
import models.Subtask;
import models.Task;
import services.Manager;
public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.getAllTasks();
        manager.printAllTask();

        System.out.println("Вывод задачи по заданному" + manager.getTaskById(0)); //распечатывает задачу по нужному id
        System.out.println("Вывод подзадачи по заданному" + manager.getSubtaskById(3)); //распечатывает подзадачу по нужному id
        System.out.println("Вывод эпика по заданному" + manager.getEpicById(6)); //печать эпика по id

        Task task = new Task() //задаем новую задачу вместо задачи с id = 0
                .setId(0)
                .setName("Позвонить другу")
                .setDescription("")
                .setStatus("IN_PROGRESS");
        manager.saveTask(task);

        Epic epic2 = new Epic(); //задаем новый эпик вместо эпика с id=6
        epic2.setId(6);
        epic2.setName("Погулять с кошкой");
        epic2.setDescription("Прогулка");
        epic2.setStatus("NEW");
        manager.saveEpic(epic2);

        Subtask subtask = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask.setId(3);
        subtask.setName("Налить воды в кастрюлю");
        subtask.setDescription("");
        subtask.setStatus("DONE");
        subtask.setEpicID(2);
        manager.saveSubtask(subtask);
        manager.printAllTask();
//Удаление задачи/подзадачи/эпика по заданному id
        manager.removeTaskById(1);
        manager.removeEpicById(6);
        manager.removeSubtaskById(3);
// проверка вывода     manager.printAllTask();
        manager.printListSubtaskIdEpic (2);
//меняем статус задачи
        manager.changeStatusTask(0, "DONE");
//меняем статус подзадачи
        manager.changeStatusSubtask(4, "DONE");
//меняем статус эпика
        manager.changeStatusEpic(2);
//проверка вывода
        manager.printAllTask();

    }
}

