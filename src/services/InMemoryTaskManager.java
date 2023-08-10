package services;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import repositories.*;
import models.Enum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;


public class InMemoryTaskManager implements TaskManager {
    public static TaskRepository tasksRepository = new TaskRepository();
    public static EpicRepository epicRepository = new EpicRepository();
    public static SubtaskRepository subtaskRepository = new SubtaskRepository();
    protected static ArrayList<Task> allTasks = new ArrayList<>();

    public static int getCOUNT() {
        return COUNT++;
    }

    private static int COUNT = 0;

    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistoryManager();


    public void validator() {
        System.out.println("*******************************" + "\n" + "Задачи, у которых нужно поменять StartTime:" + "\n");
        TreeSet<Task> sortTasksTime = new TreeSet<>(new Comparator<>() {

            @Override
            public int compare(Task o1, Task o2) {
                if (o1.getStartTime() == o2.getStartTime()) {

                } else {

                    if ((o1.getStartTime().isBefore(o2.getStartTime()))) {
                        return -1;
                    } else if ((o1.getStartTime().isAfter(o2.getStartTime()))) {
                        return 1;
                    } else {
                        System.out.println("id=[" + o1.getId() + "]" + " время начала: " + o1.getStartTime() + " совпадает с: " + "id=[" + o2.getId() + "]" + " время начала: " + o2.getStartTime() + "\n");
                        return 1;
                    }
                }
                return 0;
            }

        });
        sortTasksTime.addAll(getAllTasks());
        sortTasksTime.addAll(getAllSubtasks());
    }


    public TreeSet<Task> getPrioritizedTasks() {


        TreeSet<Task> sortTasksTime = new TreeSet<>(new Comparator<Task>() {

            @Override
            public int compare(Task o1, Task o2) {
                if ((o1.getStartTime().isBefore(o2.getStartTime()))) {
                    return -1;
                } else if ((o1.getStartTime().isAfter(o2.getStartTime()))) {
                    return 1;
                } else {
                    return 1;
                }
            }

        });


        sortTasksTime.addAll(getAllTasks());
        sortTasksTime.addAll(getAllSubtasks());

        System.out.println("*******************************" + "\n" + "Сортировка задач по времени начала:" + "\n");
        for (Task ts : sortTasksTime) {

            System.out.println(ts);
        }

        return sortTasksTime;
    }


    @Override
    public void printListSubtaskIdEpic(int id) { //печатаем подзадачи эпика по нужному id
        System.out.println("Все подзадачи эпика с id " + id + getListSubtask(id));
    }

    @Override
    public void printAllTask() { //печать всех задач (задачи+эпики+подзадачи)
        List<Task> allTasks = this.getAllTasks();
        List<Epic> allEpics = this.getAllEpics();
        List<Subtask> allSubtasks = this.getAllSubtasks();
        System.out.println("id,type,name,status,description,epic");
        for (Task task2 : allTasks) {
            System.out.println(task2.getId() + "," + Enum.TASK + "," + task2.getName() + "," + task2.getStatus() + "," + task2.getDescription());
        }

        for (Task epic3 : allEpics) {
            System.out.println(epic3.getId() + "," + Enum.EPIC + "," + epic3.getName() + "," + epic3.getStatus() + "," + epic3.getDescription());
        }
        for (Subtask subtask2 : allSubtasks) {
            System.out.println(subtask2.getId() + "," + Enum.SUBTASK + "," + subtask2.getName() + "," + subtask2.getStatus() + "," + subtask2.getDescription() + "," + subtask2.getEpicID());

        }
    }

    @Override
    public void deleteAllTasks() { //удаление вообще всех задач+эпиков+подзадач из общего списка
        allTasks.clear();
    } //удаление всех задач из общего списка

    @Override
    public void deleteAllTask() { //удалить все задачи
        tasksRepository.deleteAll();
    } //удаление всех задач из таблицы задач

    @Override
    public void deleteAllEpics() { //удалить все эпики с подзадачами
        epicRepository.deleteAll();
        subtaskRepository.deleteAll();
    } //удаление всех эпиков из таблицы эпиков

    @Override
    public void deleteAllSubtask() { //удалить все подзадачи, изменить статусы эпиков на NEW
        subtaskRepository.deleteAll();

    }

    @Override
    public void saveTask(Task task) { //сохранение новой задачи
        tasksRepository.save(task);
    } //сохранение задач в таблицу

    @Override
    public void saveSubtask(Subtask subtask) { //сохранение новой подзадачи
        subtaskRepository.save(subtask);
    } //сохранение подзадач в таблицу

    @Override
    public void saveEpic(Epic epic) { //сохранение нового эпика
        epicRepository.save(epic);
    } //сохранение эпиков в таблицу

    @Override
    public void removeTaskById(int id) { //удаление задачи по id
        if (!inMemoryHistoryManager.getHistoryHash().isEmpty()) {
            if (inMemoryHistoryManager.getHistoryHash().containsKey(id)) {
                //  inMemoryHistoryManager.remove(id);
                inMemoryHistoryManager.remove(tasksRepository.get(id));
            }
        }
        tasksRepository.removeById(id);
    }

    @Override
    public void removeEpicById(int id) { //удаление эпика по id

        for (Task t : getListSubtask(id)) {
            if (inMemoryHistoryManager.getHistoryHash().containsKey(t.getId())) {
                //  inMemoryHistoryManager.remove(t.getId());
                inMemoryHistoryManager.remove(t);
            }
        }
        epicRepository.removeById(id);
    }

    @Override
    public void removeSubtaskById(int id) { //удаление подзадачи по id с изменением статуса эпика
        if (!inMemoryHistoryManager.getHistoryHash().isEmpty()) {
            if (inMemoryHistoryManager.getHistoryHash().containsKey(id)) {
                //  inMemoryHistoryManager.remove(id);
                inMemoryHistoryManager.remove(subtaskRepository.get(id));
            }


        }
        if (!subtaskRepository.getSubtasks().isEmpty()) {
            if (subtaskRepository.getSubtasks().containsKey(id)) {

                int EpId = subtaskRepository.getSubtasks().get(id).getEpicID();
                if (subtaskRepository.getSubtasks().containsKey(id)) {
                    subtaskRepository.removeById(id);
                    if (!subtaskRepository.getSubtasks().isEmpty()) {
                        changeStatusEpic(EpId);
                    }
                } else {
                    System.out.println("Несуществующий id подзадачи");
                }
            }
        }


    }

    @Override
    public void changeStatusTask(int id, models.Status status) { //сменить статус задачи по id
        if (tasksRepository.getTasks().containsKey(id)) {
            tasksRepository.changeStatus(id, status);
        } else {
            System.out.println("Задачи с id " + id + " не существует");
        }
    }

    @Override
    public void changeStatusSubtask(int id, models.Status status) { //сменить статус подзадачи по id
        subtaskRepository.changeStatus(id, status);
    }

    @Override
    public void changeStatusEpic(int id) { //изменить статус заданного эпика со списком подзадач
        int i = 0;
        int k = 0;
        ArrayList<Subtask> subtask = subtaskRepository.getListSubtask(id);
        if (!subtask.isEmpty()) {
            for (Subtask sub : subtask) {

                if ((sub.getStatus()).toString().equals("NEW")) {
                    i++;
                }
                if ((sub.getStatus()).toString().equals("DONE")) {
                    k++;
                }
            }
            if (subtask.size() == i) {
                (epicRepository.get(id)).setStatus(Status.NEW);
            } else if (subtask.size() == k) {
                (epicRepository.get(id)).setStatus(Status.DONE);
            } else {
                (epicRepository.get(id)).setStatus(Status.IN_PROGRESS);
            }
        } else {
            if (epicRepository.get(id) != null) {
                epicRepository.get(id).setStatus(Status.NEW);
            }
        }

    }

    public List<Task> getAllallTasks() {

        allTasks.addAll(tasksRepository.getAll());
        allTasks.addAll(subtaskRepository.getAll());
        allTasks.addAll(epicRepository.getAll());
        return allTasks;
    }


    @Override
    public List<Task> getAllTasks() { //список всех задач
        return tasksRepository.getAll();
    }   //получить список всех задач

    @Override
    public List<Subtask> getAllSubtasks() { //список всех подзадач
        return subtaskRepository.getAll();
    } //получить список всех подзадач

    @Override
    public List<Epic> getAllEpics() { //спивок всех эпиков
        return epicRepository.getAll();
    } //получить список всех эпиков

    @Override
    public Task getTaskById(int id) { //получить задачу по id
        if (!tasksRepository.getTasks().containsKey(id)) {
            System.out.println("Задачи с id " + id + " нет");
        } else
            inMemoryHistoryManager.add(tasksRepository.get(id));
        return tasksRepository.get(id);

    }

    @Override
    public Epic getEpicById(int id) { //получить эпик по id
        inMemoryHistoryManager.add(epicRepository.get(id));
        //  inMemoryHistoryManager.getHistory(id, epicRepository.get(id));
        return epicRepository.get(id);

    }

    @Override
    public Subtask getSubtaskById(int id) { //получить подзадачу по id
        inMemoryHistoryManager.add(subtaskRepository.get(id));
        // inMemoryHistoryManager.getHistory(id, subtaskRepository.get(id));
        return subtaskRepository.get(id);
    }

    @Override
    public List<Subtask> getListSubtask(int id) {
        return subtaskRepository.getListSubtask(id);
    } //получить список подзадач эпика по id

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHis2();
    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }


}




