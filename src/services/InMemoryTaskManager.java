package services;
import models.Epic;
import models.Subtask;
import models.Task;
import repositories.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager{
    private TaskRepository tasksRepository = new TaskRepository();
    private EpicRepository epicRepository = new EpicRepository();
    private SubtaskRepository subtaskRepository = new SubtaskRepository();
    private ArrayList<Task> allTasks = new ArrayList<>();
    private static int COUNT = 0;
    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    @Override
    public void printListSubtaskIdEpic (int id){ //печатаем подзадачи эпика по нужному id
        System.out.println("Все подзадачи эпика с id " + id + getListSubtask(id));
    }
    @Override
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
    @Override
    public void deleteAllTasks() { //удаление вообще всех задач+эпиков+подзадач из общего списка
        allTasks.clear();
    } //удаление всех задач из общего списка
    @Override
    public void deleteAllTask() { //удалить все задачи
        tasksRepository.deleteAll();
    } //удаление всех задач из таблицы задач
    @Override
    public void deleteAllEpics() { //удалить все эпики
        epicRepository.deleteAll();
    } //удаление всех эпиков из таблицы эпиков
    @Override
    public void deleteAllSubtask() { //удалить все подзадачи
        subtaskRepository.deleteAll();
    } //удаление всех подзадач из таблицы подзадач
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
        inMemoryHistoryManager.remove(id);  tasksRepository.removeById(id);
    } //удаление задачи по id
    @Override
    public void removeEpicById(int id) { //удаление эпика по id

        for (Task t: getListSubtask(id)){
           if (inMemoryHistoryManager.getHistoryHash().containsKey(t.getId())){
               inMemoryHistoryManager.remove(t.getId());}
        }
        epicRepository.removeById(id);
    } //удаление эпика по id
    @Override
    public void removeSubtaskById(int id) { //удаление подзадачи по id
        inMemoryHistoryManager.remove(id);  subtaskRepository.removeById(id);
    } //удаление подзадачи по id
    @Override
    public void changeStatusTask(int id, models.Status status) { //сменить статус задачи по id
        tasksRepository.changeStatus(id, status);
    }
    @Override
    public void changeStatusSubtask(int id, models.Status status) { //сменить статус подзадачи по id
        subtaskRepository.changeStatus(id, status);
    }
    @Override
    public void changeStatusEpic(int id) { //изменить статус заданного эпика
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
            (epicRepository.get(id)).setStatus(models.Status.NEW);
        } else if (subtask.size() == k) {
            (epicRepository.get(id)).setStatus(models.Status.DONE);
        } else {
            (epicRepository.get(id)).setStatus(models.Status.IN_PROGRESS);
        }

    }

    @Override
    public ArrayList<Task> getAllTasks() { //заполнение списка всех задач
        Task task = new Task()
                .setId(COUNT++)
                .setName("Позвонить подруге")
                .setDescription("")
                .setStatus(models.Status.IN_PROGRESS);

        Task otherTask = new Task()
                .setId(COUNT++)
                .setName("Забрать посылку")
                .setDescription("Забрать посылку на почте")
                .setStatus(models.Status.IN_PROGRESS);

        Epic epic = new Epic();
        epic.setId(COUNT++);
        epic.setName("Сварить пельмени");
        epic.setDescription("Рецепт пельменей");
        epic.setStatus(models.Status.IN_PROGRESS);

        Subtask subtask = new Subtask();
        subtask.setId(COUNT++);
        epic.setIdSubtask(subtask.getId());
        subtask.setName("Налить воду в кастрюлю");
        subtask.setDescription("Налить воды");
        subtask.setStatus(models.Status.IN_PROGRESS);
        subtask.setEpicID(epic.getId());

        Subtask subtask2 = new Subtask();
        subtask2.setId(COUNT++);
        epic.setIdSubtask(subtask2.getId());
        subtask2.setName("Дождатья кипения воды");
        subtask2.setDescription("Кипящая вода");
        subtask2.setStatus(models.Status.NEW);
        subtask2.setEpicID(epic.getId());

        Subtask subtask4 = new Subtask();
        subtask4.setId(COUNT++);
        epic.setIdSubtask(subtask4.getId());
        subtask4.setName("Бросить пельмени");
        subtask4.setDescription("Пельмени");
        subtask4.setStatus(models.Status.NEW);
        subtask4.setEpicID(epic.getId());

        Epic epic2 = new Epic();
        epic2.setId(COUNT++);
        epic2.setName("Погулять с собакой");
        epic2.setDescription("Прогулка");
        epic2.setStatus(models.Status.NEW);

        Subtask subtask3 = new Subtask();
        subtask3.setId(COUNT++);
        subtask3.setName("Одеться");
        subtask3.setDescription("Одеться по погоде");
        subtask3.setStatus(models.Status.NEW);
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


     // Задание спринта 5:
        // создать 2 задачи
        Task task9 = new Task() //задаем новую задачу вместо задачи с id = 0
                .setId(9)
                .setName("Помыть посуду")
                .setDescription("")
                .setStatus(models.Status.IN_PROGRESS);
        tasksRepository.save(task9);
        Task task10 = new Task() //задаем новую задачу вместо задачи с id = 0
                .setId(10)
                .setName("Помыть посуду")
                .setDescription("")
                .setStatus(models.Status.IN_PROGRESS);
        tasksRepository.save(task10);
        //эпик с 3 подзадачами
        Epic epic11 = new Epic(); //задаем новый эпик вместо эпика с id=6
        epic11.setId(11);
        epic11.setName("Собрать вещи для переезда");
        epic11.setDescription("Прогулка");
        epic11.setStatus(models.Status.NEW);
        epicRepository.save(epic11);
        //3 подзадачи эпика
        Subtask subtask12 = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask12.setId(12);
        subtask12.setName("Приготовить чемоданы");
        subtask12.setDescription("");
        subtask12.setStatus(models.Status.DONE);
        subtask12.setEpicID(11);
        subtaskRepository.save(subtask12);

        Subtask subtask13 = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask13.setId(13);
        subtask13.setName("Собрать вещи");
        subtask13.setDescription("");
        subtask13.setStatus(models.Status.DONE);
        subtask13.setEpicID(11);
        subtaskRepository.save(subtask13);

        Subtask subtask14 = new Subtask(); //задаем новую подзадачу вместо подзадачи с id=3
        subtask14.setId(14);
        subtask14.setName("Упаковать вещи");
        subtask14.setDescription("");
        subtask14.setStatus(models.Status.DONE);
        subtask14.setEpicID(11);
        subtaskRepository.save(subtask14);
        //эпик без подзадач
        Epic epic15 = new Epic(); //задаем новый эпик вместо эпика с id=6
        epic15.setId(15);
        epic15.setName("Вызвать такси");
        epic15.setDescription("Прогулка");
        epic15.setStatus(models.Status.NEW);
        epicRepository.save(epic15);


        return allTasks;
    }
    @Override
    public ArrayList<Task> getAllTasks2() { //список всех задач
        return tasksRepository.getAll();
    }   //получить список всех задач
    @Override
    public ArrayList<Task> getAllSubtasks() { //список всех подзадач
        return subtaskRepository.getAll();
    } //получить список всех подзадач
    @Override
    public ArrayList<Task> getAllEpics() { //спивок всех эпиков
        return epicRepository.getAll();
    } //получить список всех эпиков
    @Override
    public Task getTaskById(int id) { //получить задачу по id
        inMemoryHistoryManager.add(tasksRepository.get(id));
     //   inMemoryHistoryManager.getHistory(id, tasksRepository.get(id));
        return tasksRepository.get(id);
    }
    @Override
    public Task getEpicById(int id) { //получить эпик по id
        inMemoryHistoryManager.add(epicRepository.get(id));
      //  inMemoryHistoryManager.getHistory(id, epicRepository.get(id));
        return epicRepository.get(id); }
    @Override
    public Task getSubtaskById(int id) { //получить подзадачу по id
        inMemoryHistoryManager.add(subtaskRepository.get(id));
       // inMemoryHistoryManager.getHistory(id, subtaskRepository.get(id));
        return subtaskRepository.get(id);
    }
    @Override
    public ArrayList<Subtask> getListSubtask(int id) { return subtaskRepository.getListSubtask(id); } //получить список подзадач эпика по id
    @Override
    public List<Task> getHistory(int id, Task task) {return InMemoryHistoryManager.viewTask;};
    }





