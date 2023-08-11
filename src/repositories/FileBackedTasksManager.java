package repositories;

import models.*;
import models.Enum;
import services.InMemoryTaskManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static models.Enum.*;
import static services.InMemoryHistoryManager.historyHash;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;
    private Enum Enum;

    public FileBackedTasksManager() {
        this.file = null;
    }

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager tasksManager = new FileBackedTasksManager(file);
        tasksManager.load();
        return tasksManager;
    }

    public static class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(final String message) {
            super(message);
        }
    }

    public void save() {
        try {

            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            System.out.println("id,type,name,status,description,epic,startTime,duration,endTime");
            fileWriter.write("id,type,name,status,description,epic,startTime,duration,endTime\n");

            for (Task task2 : getAllTasks()) {

                System.out.println(task2.getId() + "," + TASK + "," + task2.getName() + "," + task2.getStatus() + "," + task2.getDescription() + "," + task2.getId() + "," + task2.getStartTime() + "," + task2.getDuration() + "," + task2.getEndTime());
                fileWriter.write(task2.getId() + "," + TASK + "," + task2.getName() + "," + task2.getStatus() + "," + task2.getDescription() + "," + task2.getId() + "," + task2.getStartTime() + "," + task2.getDuration() + "," + task2.getEndTime() + "\n");
            }

            for (Epic epic3 : getAllEpics()) {
                System.out.println(epic3.getId() + "," + EPIC + "," + epic3.getName() + "," + epic3.getStatus() + "," + epic3.getDescription() + "," + epic3.getId() + "," + epic3.getStartTimeEpic(epic3.getId()) + "," + epic3.getDurationEpic(epic3.getId()) + "," + epic3.getEndTimeEpic(epic3.getId()));
                fileWriter.write(epic3.getId() + "," + EPIC + "," + epic3.getName() + "," + epic3.getStatus() + "," + epic3.getDescription() + "," + epic3.getId() + "," + epic3.getStartTimeEpic(epic3.getId()) + "," + epic3.getDurationEpic(epic3.getId()) + "," + epic3.getEndTimeEpic(epic3.getId()) + "\n");

            }

            for (Subtask subtask2 : getAllSubtasks()) {
                System.out.println(subtask2.getId() + "," + SUBTASK + "," + subtask2.getName() + "," + subtask2.getStatus() + "," + subtask2.getDescription() + "," + subtask2.getEpicID() + "," + subtask2.getStartTime() + "," + subtask2.getDuration() + "," + subtask2.getEndTime());
                fileWriter.write(subtask2.getId() + "," + SUBTASK + "," + subtask2.getName() + "," + subtask2.getStatus() + "," + subtask2.getDescription() + "," + subtask2.getEpicID() + "," + subtask2.getStartTime() + "," + subtask2.getDuration() + "," + subtask2.getEndTime() + "\n");

            }
            fileWriter.write("\n");
            String result = getInMemoryHistoryManager().getHistoryHash().keySet().toString();
            fileWriter.write(result.substring(1, result.length() - 1));
            System.out.println("История просмотра: " + result.substring(1, result.length() - 1));
            fileWriter.close();


        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }

    }


    public void load() {
        try { //id,type,name,status,description,epic,startTime,duration,endTime

            String content = Files.readString(Paths.get("file.csv"));
            String[] str = content.split("\n");
            if (str.length > 1) {
                for (int i = 1; i < str.length - 2; i++) {
                    String str1 = str[i];
                    String[] parts = str1.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    String name = parts[2];
                    String status = parts[3];
                    String description = parts[4];
                    int epic = Integer.parseInt(parts[5]);
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime startTime = LocalDateTime.parse(parts[6], format);
                    int duration = Integer.parseInt(parts[7]);
                    LocalDateTime endTime = LocalDateTime.parse(parts[8], format);

                    if (type.equals("TASK")) {//int id, String name, String description, int duration, LocalDateTime startTime, LocalDateTime endTime, Status status
                        Task task = new Task();
                        task.setId(id);
                        task.setName(name);
                        task.setStatus(Status.valueOf(status));
                        task.setDescription(description);
                        task.setStartTime(startTime);
                        task.setDuration(duration);
                        task.setEndTime(endTime);
                        tasksRepository.save(task);
                    }
                    if (type.equals("EPIC")) {
                        Epic epics = new Epic();
                        epics.setId(id);
                        epics.setName(name);
                        epics.setStatus(Status.valueOf(status));
                        epics.setDescription(description);
                        epics.setStartTimeEpic(startTime);
                        epics.setDurationEpic(duration);
                        epics.setEndTimeEpic(endTime);
                        epicRepository.save(epics);
                    }
                    if (type.equals("SUBTASK")) {
                        Subtask subtask = new Subtask();
                        subtask.setId(id);
                        subtask.setName(name);
                        subtask.setStatus(Status.valueOf(status));
                        subtask.setDescription(description);
                        subtask.setEpicID(epic);
                        subtask.setStartTime(startTime);
                        subtask.setDuration(duration);
                        subtask.setEndTime(endTime);
                        subtaskRepository.save(subtask);
                    }
                }
                System.out.println("*******************************" + "\n" + "Выгрузка из файла");
                System.out.println("Вывод задач из файла: " + "\n" + tasksRepository.getTasks());
                System.out.println("Вывод эпиков из файла: " + "\n" + epicRepository.getEpics());
                System.out.println("Вывод подзадач из файла: " + "\n" + subtaskRepository.getSubtasks());

                String[] str1 = str[str.length - 1].split(", ");
                if (str[str.length - 2].isBlank()) {
                    for (String s : str1) {
                        int l = Integer.parseInt(s);
                        if (tasksRepository.getTasks().containsKey(l) && (!tasksRepository.getTasks().isEmpty())) {
                            //historyHash.put(l, inMemoryHistoryManager.linkLast(tasksRepository.getTasks().get(l)));
                            getTaskById(l);


                        }
                        if (subtaskRepository.getSubtasks().containsKey(l) && (!subtaskRepository.getSubtasks().isEmpty())) {
                            //  historyHash.put(l, inMemoryHistoryManager.linkLast(subtaskRepository.getSubtasks().get(l)));
                            getSubtaskById(l);
                        }
                        if (epicRepository.getEpics().containsKey(l) && (!epicRepository.getEpics().isEmpty())) {
                            //   historyHash.put(l, inMemoryHistoryManager.linkLast(epicRepository.getEpics().get(l)));
                            getEpicById(l);
                        }
                    }
                    System.out.println("Задачи из истории: " + historyHash.keySet() + historyHash.values());

                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения из файла");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        InMemoryTaskManager manager = Managers.getDefault();

        /* Тут создаем файл из задач, в главном main загружаются задачи в программу из этого файла*/

        File file = new File("file.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        Task task6 = new Task()
                .setId(1)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        task6.setDuration(2);
        task6.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
        fileBackedTasksManager.saveTask(task6);
        InMemoryTaskManager.allTasks.add(task6);

        Task task7 = new Task()
                .setId(9)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        task7.setDuration(25);
        task7.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
        fileBackedTasksManager.saveTask(task7);
        InMemoryTaskManager.allTasks.add(task7);


        Epic epic6 = new Epic();
        epic6.setId(2);
        epic6.setName("Сделать уборку");
        epic6.setDescription("Сделать уборку");
        epic6.setStatus(models.Status.IN_PROGRESS);
        fileBackedTasksManager.saveEpic(epic6);
        InMemoryTaskManager.allTasks.add(epic6);


        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        epic6.setIdSubtask(subtask9.getId());
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(models.Status.NEW);
        subtask9.setEpicID(epic6.getId());
        subtask9.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 10, 0, 0));
        subtask9.setDuration(10);
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        fileBackedTasksManager.changeStatusEpic(2);


        System.out.println("Вывод эпика по заданному id: " + manager.getEpicById(2));
        System.out.println("Вывод задачи по заданному id: " + manager.getTaskById(1));
        System.out.println("Вывод подзадачи по заданному id: " + manager.getSubtaskById(3));
        //   inMemoryTaskManager.deleteAllTask();
        //   inMemoryTaskManager.deleteAllEpics();
        //   inMemoryTaskManager.deleteAllSubtask();
        fileBackedTasksManager.removeTaskById(9);

        fileBackedTasksManager.validator();
        fileBackedTasksManager.save();

    }

    public void saveTask(Task task) {
        super.saveTask(task);
        save();
    }

    public void saveEpic(Epic epic) {
        super.saveEpic(epic);
        save();
    }

    public void saveSubtask(Subtask subtask) {
        super.saveSubtask(subtask);
        save();
    }

    public void deleteAllTasks() { //удаление вообще всех задач+эпиков+подзадач из общего списка
        super.deleteAllTasks();
        save();

    } //удаление всех задач из общего списка

    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    public void changeStatusEpic(int id) {
        super.changeStatusEpic(id);
        save();

    }

    public void changeStatusTask(int id, models.Status status) {
        super.changeStatusTask(id, status);
        save();
    }

    public void changeStatusSubtask(int id, models.Status status) {
        super.changeStatusSubtask(id, status);
        save();
    }

    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    public List<Subtask> getListSubtask(int id) {
        List<Subtask> listSubtask = getListSubtask(id);
        save();
        return listSubtask;
    }

    public List<Task> getHistory() {
        List<Task> getHistory = getHistory();
        save();
        return getHistory;
    }


}


