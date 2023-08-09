package services;


import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import repositories.*;
import models.Enum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    public static TaskRepository tasksRepository = new TaskRepository();
    protected static EpicRepository epicRepository = new EpicRepository();
    protected static SubtaskRepository subtaskRepository = new SubtaskRepository();
    protected static ArrayList<Task> allTasks = new ArrayList<>();
 //  private final HistoryManager historyManager = new InMemoryHistoryManager();

  /*  public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }*/


    public static int getCOUNT() {
        return COUNT++;
    }

    private static int COUNT = 0;

    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();


    @Override
    public void printListSubtaskIdEpic(int id) { //печатаем подзадачи эпика по нужному id
        System.out.println("Все подзадачи эпика с id " + id + getListSubtask(id));
    }

    @Override
    public void printAllTask() { //печать всех задач (задачи+эпики+подзадачи)
        ArrayList<Task> allTasks = this.getAllTasks2();
        ArrayList<Epic> allEpics = this.getAllEpics();
        ArrayList<Subtask> allSubtasks = this.getAllSubtasks();
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
        }
        else
        { if (epicRepository.get(id)!=null) {epicRepository.get(id).setStatus(Status.NEW);}}

    }


 public ArrayList<Task> getAllTasks() {
     allTasks.addAll(tasksRepository.getAll());
     allTasks.addAll(subtaskRepository.getAll());
     allTasks.addAll(epicRepository.getAll());



   /*  Task task = new Task()
             .setId(COUNT++)
             .setName("Позвонить подруге")
             .setDescription("Description")
             .setStatus(models.Status.IN_PROGRESS);
     tasksRepository.save(task);
     allTasks.add(task);


        //заполнение списка всех задач отключила, т.к. загружаются из файла
    /*    Task task = new Task()
                .setId(COUNT++)
                .setName("Позвонить подруге")
                .setDescription("Description")
                .setStatus(models.Status.IN_PROGRESS);
        tasksRepository.save(task);
        allTasks.add(task);

        Task task2 = new Task()
                .setId(COUNT++)
                .setName("Забрать посылку")
                .setDescription("Забрать посылку на почте")
                .setStatus(models.Status.IN_PROGRESS);
        tasksRepository.save(task2);
        allTasks.add(task2);

        Epic epic = new Epic();
        epic.setId(COUNT++);
        epic.setName("Сварить пельмени");
        epic.setDescription("Рецепт пельменей");
        epic.setStatus(models.Status.IN_PROGRESS);
        epicRepository.save(epic);
        allTasks.add(epic);

        Subtask subtask = new Subtask();
        subtask.setId(COUNT++);
        epic.setIdSubtask(subtask.getId());
        subtask.setName("Налить воду в кастрюлю");
        subtask.setDescription("Налить воды");
        subtask.setStatus(models.Status.IN_PROGRESS);
        subtask.setEpicID(epic.getId());
        subtaskRepository.save(subtask);
        allTasks.add(subtask);

        Subtask subtask2 = new Subtask();
        subtask2.setId(COUNT++);
        epic.setIdSubtask(subtask2.getId());
        subtask2.setName("Дождатья кипения воды");
        subtask2.setDescription("Кипящая вода");
        subtask2.setStatus(models.Status.NEW);
        subtask2.setEpicID(epic.getId());
        subtaskRepository.save(subtask2);
        allTasks.add(subtask2);

        Subtask subtask3 = new Subtask();
        subtask3.setId(COUNT++);
        epic.setIdSubtask(subtask3.getId());
        subtask3.setName("Бросить пельмени");
        subtask3.setDescription("Пельмени");
        subtask3.setStatus(models.Status.NEW);
        subtask3.setEpicID(epic.getId());
        subtaskRepository.save(subtask3);
        allTasks.add(subtask3);

        Epic epic2 = new Epic();
        epic2.setId(COUNT++);
        epic2.setName("Погулять с собакой");
        epic2.setDescription("Прогулка");
        epic2.setStatus(models.Status.NEW);
        epicRepository.save(epic2);
        allTasks.add(epic2);

        Subtask subtask4 = new Subtask();
        subtask4.setId(COUNT++);
        epic2.setIdSubtask(subtask4.getId());
        subtask4.setName("Одеться");
        subtask4.setDescription("Одеться по погоде");
        subtask4.setStatus(models.Status.NEW);
        subtask4.setEpicID(epic2.getId());
        subtaskRepository.save(subtask4);
        allTasks.add(subtask4);

        Task task3 = new Task() //задаем новую задачу вместо задачи с id = 0
                .setId(COUNT++)
                .setName("Позвонить другу")
                .setDescription("Description")
                .setStatus(models.Status.IN_PROGRESS);
        tasksRepository.save(task3);
        allTasks.add(task3);


        Epic epic3 = new Epic(); //задаем новый эпик вместо эпика с id=6
        epic3.setId(COUNT++);
        epic3.setName("Погулять с кошкой");
        epic3.setDescription("Прогулка");
        epic3.setStatus(models.Status.NEW);
        epicRepository.save(epic3);
        allTasks.add(epic3);

        Subtask subtask5 = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask5.setId(COUNT++);
        subtask5.setName("Налить воды в кастрюлю");
        subtask5.setDescription("Description");
        subtask5.setStatus(models.Status.DONE);
        subtask5.setEpicID(epic3.getId());
        subtaskRepository.save(subtask5);
        allTasks.add(subtask5);

        // Задание спринта 5:
        // создать 2 задачи
        Task task4 = new Task() //задаем новую задачу вместо задачи с id = 0
                .setId(COUNT++)
                .setName("Помыть посуду")
                .setDescription("Description")
                .setStatus(models.Status.IN_PROGRESS);
        tasksRepository.save(task4);
        allTasks.add(task4);
        Task task5 = new Task() //задаем новую задачу вместо задачи с id = 0
                .setId(10)
                .setName("Помыть посуду")
                .setDescription("Description")
                .setStatus(models.Status.IN_PROGRESS);
        tasksRepository.save(task5);
        allTasks.add(task5);
        //эпик с 3 подзадачами
        Epic epic4 = new Epic(); //задаем новый эпик вместо эпика с id=6
        epic4.setId(COUNT++);
        epic4.setName("Собрать вещи для переезда");
        epic4.setDescription("Прогулка");
        epic4.setStatus(models.Status.NEW);
        epicRepository.save(epic4);
        allTasks.add(epic4);
        //3 подзадачи эпика
        Subtask subtask6 = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask6.setId(COUNT++);
        subtask6.setName("Приготовить чемоданы");
        subtask6.setDescription("Description");
        subtask6.setStatus(models.Status.DONE);
        subtask6.setEpicID(epic4.getId());
        subtaskRepository.save(subtask6);
        allTasks.add(subtask6);

        Subtask subtask7 = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask7.setId(COUNT++);
        subtask7.setName("Собрать вещи");
        subtask7.setDescription("Description");
        subtask7.setStatus(models.Status.DONE);
        subtask7.setEpicID(epic4.getId());
        subtaskRepository.save(subtask7);
        allTasks.add(subtask7);

        Subtask subtask8 = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask8.setId(COUNT++);
        subtask8.setName("Упаковать вещи");
        subtask8.setDescription("Description");
        subtask8.setStatus(models.Status.DONE);
        subtask8.setEpicID(epic4.getId());
        subtaskRepository.save(subtask8);
        allTasks.add(subtask8);
        //эпик без подзадач
        Epic epic5 = new Epic(); //задаем новый эпик вместо эпика с id=6
        epic5.setId(15);
        epic5.setName("Вызвать такси");
        epic5.setDescription("Прогулка");
        epic5.setStatus(models.Status.NEW);
        epicRepository.save(epic5);
        allTasks.add(epic5);

*/
        return allTasks;
    }

    @Override
    public ArrayList<Task> getAllTasks2() { //список всех задач
        return tasksRepository.getAll();
    }   //получить список всех задач

    @Override
    public ArrayList<Subtask> getAllSubtasks() { //список всех подзадач
        return subtaskRepository.getAll();
    } //получить список всех подзадач

    @Override
    public ArrayList<Epic> getAllEpics() { //спивок всех эпиков
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
    public Task getSubtaskById(int id) { //получить подзадачу по id
        inMemoryHistoryManager.add(subtaskRepository.get(id));
        // inMemoryHistoryManager.getHistory(id, subtaskRepository.get(id));
        return subtaskRepository.get(id);
    }

    @Override
    public ArrayList<Subtask> getListSubtask(int id) {
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




