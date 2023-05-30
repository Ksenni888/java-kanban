package services;
import Models.Epic;
import Models.Subtask;
import Models.Task;
import repositories.*;
import java.util.ArrayList;
public class Manager {
    private TaskRepository tasksRepository = new TaskRepository();
    private EpicRepository epicRepository = new EpicRepository();
    private SubtaskRepository subtaskRepository = new SubtaskRepository();
    private ArrayList<Task> allTasks = new ArrayList<>();
    private static int COUNT = 0;

    public void printListSubtaskIdEpic (int id){ //печатаем подзадачи эпика по нужному id
        System.out.println("Все подзадачи эпика с id " + id + getListSubtask(id));
    }
    public void printAllTask() { //печать всех задач (задачи+эпики+подзадачи)
        ArrayList<Task> allTasks = this.getAllTasks2();
        ArrayList<Task> allEpics = this.getAllEpics();
        ArrayList<Task> allSubtasks = this.getAllSubtasks();
        for (Task task2 : allTasks) {
            System.out.println("Простая задача: " + task2);
        }

        for (Task epic3 : allEpics) {
            System.out.println("Эпик: " + epic3);
        }
        for (Task subtask2 : allSubtasks) {
            System.out.println("Подзадача: " + subtask2);
        }
    }
    public ArrayList<Task> getAllTasks() { //заполнение списка всех задач
        Task task = new Task()
                .setId(COUNT++)
                .setName("Позвонить подруге")
                .setDescription("")
                .setStatus("IN_PROGRESS");

        Task otherTask = new Task()
                .setId(COUNT++)
                .setName("Забрать посылку")
                .setDescription("Забрать посылку на почте")
                .setStatus("IN_PROGRESS");

        Epic epic = new Epic();
        epic.setId(COUNT++);
        epic.setName("Сварить пельмени");
        epic.setDescription("Рецепт пельменей");
        epic.setStatus("IN_PROGRESS");

        Subtask subtask = new Subtask();
        subtask.setId(COUNT++);
        epic.setIdSubtask(subtask.getId());
        subtask.setName("Налить воду в кастрюлю");
        subtask.setDescription("Налить воды");
        subtask.setStatus("IN_PROGRESS");
        subtask.setEpicID(epic.getId());

        Subtask subtask2 = new Subtask();
        subtask2.setId(COUNT++);
        epic.setIdSubtask(subtask2.getId());
        subtask2.setName("Дождатья кипения воды");
        subtask2.setDescription("Кипящая вода");
        subtask2.setStatus("NEW");
        subtask2.setEpicID(epic.getId());

        Subtask subtask4 = new Subtask();
        subtask4.setId(COUNT++);
        epic.setIdSubtask(subtask4.getId());
        subtask4.setName("Бросить пельмени");
        subtask4.setDescription("Пельмени");
        subtask4.setStatus("NEW");
        subtask4.setEpicID(epic.getId());

        Epic epic2 = new Epic();
        epic2.setId(COUNT++);
        epic2.setName("Погулять с собакой");
        epic2.setDescription("Прогулка");
        epic2.setStatus("NEW");

        Subtask subtask3 = new Subtask();
        subtask3.setId(COUNT++);
        subtask3.setName("Одеться");
        subtask3.setDescription("Одеться по погоде");
        subtask3.setStatus("NEW");
        subtask3.setEpicID(epic2.getId());
        epic2.setIdSubtask(subtask3.getId());

        allTasks.add(task);
        allTasks.add(otherTask);
        tasksRepository.save(task);
        tasksRepository.save(otherTask);

        allTasks.add(epic);
        epicRepository.save(epic);
        epicRepository.save(epic2);

        allTasks.add(subtask);
        allTasks.add(subtask2);
        subtaskRepository.save(subtask);
        subtaskRepository.save(subtask2);
        subtaskRepository.save(subtask3);
        subtaskRepository.save(subtask4);

        return allTasks;
    }
    public void deleteAllTasks() { //удаление вообще всех задач+эпиков+подзадач из общего списка
        allTasks.clear();
    } //удаление всех задач из общего списка
    public void deleteAllTask() { //удалить все задачи
        tasksRepository.deleteAll();
    } //удаление всех задач из таблицы задач
    public void deleteAllEpics() { //удалить все эпики
        epicRepository.deleteAll();
    } //удаление всех эпиков из таблицы эпиков
    public void deleteAllSubtask() { //удалить все подзадачи
        subtaskRepository.deleteAll();
    } //удаление всех подзадач из таблицы подзадач
    public void saveTask(Task task) { //сохранение новой задачи
        tasksRepository.save(task);
    } //сохранение задач в таблицу
    public void saveSubtask(Subtask subtask) { //сохранение новой подзадачи
        subtaskRepository.save(subtask);
    } //сохранение подзадач в таблицу
    public void saveEpic(Epic epic) { //сохранение нового эпика
        epicRepository.save(epic);
    } //сохранение эпиков в таблицу
    public void removeTaskById(int id) { //удаление задачи по id
        tasksRepository.removeById(id);
    } //удаление задачи по id
    public void removeEpicById(int id) { //удаление эпика по id
        epicRepository.removeById(id);
    } //удаление эпика по id
    public void removeSubtaskById(int id) { //удаление подзадачи по id
        subtaskRepository.removeById(id);
    } //удаление подзадачи по id
    public void changeStatusTask(int id, String newStatus) { //сменить статус задачи по id
        tasksRepository.changeStatus(id, newStatus);
    }
    public void changeStatusSubtask(int id, String newStatus) { //сменить статус подзадачи по id
        subtaskRepository.changeStatus(id, newStatus);
    }
    public void changeStatusEpic(int id) { //изменить статус заданного эпика
        String status;
        int i = 0;
        int k = 0;
        ArrayList<Subtask> subtask = subtaskRepository.getListSubtask(id);
        for (Subtask sub : subtask) {

            if (sub.getStatus().equals("NEW")) {
                i++;
            }
            if (sub.getStatus().equals("DONE")) {
                k++;
            }
        }
        if (subtask.size() == i) {
            status = "NEW";
            (epicRepository.get(id)).setStatus(status);
        } else if (subtask.size() == k) {
            status = "DONE";
            (epicRepository.get(id)).setStatus(status);
        } else {
            status = "IN_PROGRESS";
            (epicRepository.get(id)).setStatus(status);
        }

    }
    public ArrayList<Task> getAllTasks2() { //список всех задач
        return tasksRepository.getAll();
    }   //получить список всех задач
    public ArrayList<Task> getAllSubtasks() { //список всех подзадач
        return subtaskRepository.getAll();
    } //получить список всех подзадач
    public ArrayList<Task> getAllEpics() { //спивок всех эпиков
        return epicRepository.getAll();
    } //получить список всех эпиков
    public Task getTaskById(int id) { //получить задачу по id
        return tasksRepository.get(id);
    } //получить задачу по id
    public Task getEpicById(int id) {  return epicRepository.get(id); } //получить эпик по id
    public Task getSubtaskById(int id) { //получить подзадачу по id
        return subtaskRepository.get(id);
    } //получить подзадачу id
    public ArrayList<Subtask> getListSubtask(int id) { return subtaskRepository.getListSubtask(id); } //получить список подзадач эпика по id

   }





