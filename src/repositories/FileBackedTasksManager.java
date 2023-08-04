package repositories;

import models.*;
import models.Enum;
import services.InMemoryHistoryManager;
import services.InMemoryTaskManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.TreeSet;

import static models.Enum.*;
import static services.InMemoryHistoryManager.historyHash;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    private Enum Enum;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    protected static InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    protected static InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    public static class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(final String message) {
            super(message);
        }
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            System.out.println("id,type,name,status,description,epic,startTime,duration,endTime");
            fileWriter.write("id,type,name,status,description,epic,startTime,duration,endTime\n");

            for (Task task2 : inMemoryTaskManager.getAllTasks2()) {

                System.out.println(task2.getId() + "," +TASK + "," + task2.getName() + "," + task2.getStatus() + "," + task2.getDescription() + "," + task2.getId() + "," + task2.getStartTime() + "," + task2.getDuration() + "," + task2.getEndTime());
                fileWriter.write(task2.getId() + "," + TASK + "," + task2.getName() + "," + task2.getStatus() + "," + task2.getDescription() + "," + task2.getId() + "," + task2.getStartTime() + "," + task2.getDuration() + "," + task2.getEndTime() + "\n");
            }

            for (Epic epic3 : inMemoryTaskManager.getAllEpics()) {
                System.out.println(epic3.getId() + "," + EPIC + "," + epic3.getName() + "," + epic3.getStatus() + "," + epic3.getDescription() + "," + epic3.getId() + "," + epic3.getStartTimeEpic(epic3.getId()) + "," + epic3.getDurationEpic(epic3.getId()) + "," + epic3.getEndTimeEpic(epic3.getId()));
                fileWriter.write(epic3.getId() + "," + EPIC + "," + epic3.getName() + "," + epic3.getStatus() + "," + epic3.getDescription() + "," + epic3.getId() + "," + epic3.getStartTimeEpic(epic3.getId()) + "," + epic3.getDurationEpic(epic3.getId()) + "," + epic3.getEndTimeEpic(epic3.getId()) + "\n");

            }

            for (Subtask subtask2 : inMemoryTaskManager.getAllSubtasks()) {
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


    public static void loadFromFile(File file) {
        try { //id,type,name,status,description,epic,startTime,duration,endTime

            String content = Files.readString(Paths.get(file.getPath()));
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

                    if (type.equals("TASK")) {
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
                            historyHash.put(l, inMemoryHistoryManager.linkLast(tasksRepository.getTasks().get(l)));

                        }
                        if (subtaskRepository.getSubtasks().containsKey(l) && (!subtaskRepository.getSubtasks().isEmpty())) {
                            historyHash.put(l, inMemoryHistoryManager.linkLast(subtaskRepository.getSubtasks().get(l)));

                        }
                        if (epicRepository.getEpics().containsKey(l) && (!epicRepository.getEpics().isEmpty())) {
                            historyHash.put(l, inMemoryHistoryManager.linkLast(epicRepository.getEpics().get(l)));

                        }
                    }
                    System.out.println("Задачи из истории: " + historyHash.keySet() + historyHash.values());

                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения из файла");
        }
    }


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
        sortTasksTime.addAll(inMemoryTaskManager.getAllTasks2());
        sortTasksTime.addAll(inMemoryTaskManager.getAllSubtasks());
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


        sortTasksTime.addAll(inMemoryTaskManager.getAllTasks2());
        sortTasksTime.addAll(inMemoryTaskManager.getAllSubtasks());

        System.out.println("*******************************" + "\n" + "Сортировка задач по времени начала:" + "\n");
        for (Task ts : sortTasksTime) {

            System.out.println(ts);
        }

        return sortTasksTime;
    }


    public static void main(String[] args) {
        InMemoryTaskManager manager = (InMemoryTaskManager) Managers.getDefault();
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
        InMemoryTaskManager.tasksRepository.save(task6);

        InMemoryTaskManager.allTasks.add(task6);

        Task task7 = new Task()
                .setId(9)
                .setName("Отвести ребенка в садик")
                .setDescription("Отвести ребенка в садик")
                .setStatus(models.Status.IN_PROGRESS);
        task7.setDuration(25);
        task7.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
        InMemoryTaskManager.tasksRepository.save(task7);
        InMemoryTaskManager.allTasks.add(task7);


        Epic epic6 = new Epic();
        epic6.setId(2);
        epic6.setName("Сделать уборку");
        epic6.setDescription("Сделать уборку");
        epic6.setStatus(models.Status.IN_PROGRESS);
        InMemoryTaskManager.epicRepository.save(epic6);
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

        Subtask subtask90 = new Subtask();
        subtask90.setId(6);
        epic6.setIdSubtask(subtask9.getId());
        subtask90.setName("Вымыть полы");
        subtask90.setDescription("Вымыть полы");
        subtask90.setStatus(models.Status.NEW);
        subtask90.setEpicID(epic6.getId());
        subtask90.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 50, 0, 0));
        subtask90.setDuration(10);
        InMemoryTaskManager.subtaskRepository.save(subtask90);
        InMemoryTaskManager.allTasks.add(subtask90);


        Subtask subtask10 = new Subtask();
        subtask10.setId(8);
        epic6.setIdSubtask(subtask10.getId());
        subtask10.setName("Протереть пыль");
        subtask10.setDescription("Протереть пыль");
        subtask10.setStatus(Status.IN_PROGRESS);
        subtask10.setEpicID(epic6.getId());
        subtask10.setStartTime(null);
        subtask10.setDuration(20);
        InMemoryTaskManager.subtaskRepository.save(subtask10);
        InMemoryTaskManager.allTasks.add(subtask10);


        inMemoryTaskManager.changeStatusEpic(2);


        System.out.println("Вывод эпика по заданному id: " + manager.getEpicById(2));
        System.out.println("Вывод задачи по заданному id: " + manager.getTaskById(9));
        System.out.println("Вывод подзадачи по заданному id: " + manager.getSubtaskById(3));
        System.out.println("Вывод подзадачи по заданному id: " + manager.getSubtaskById(6));
        //   inMemoryTaskManager.deleteAllTask();
        //   inMemoryTaskManager.deleteAllEpics();
        //   inMemoryTaskManager.deleteAllSubtask();
        fileBackedTasksManager.removeSubtaskById(0);
        fileBackedTasksManager.removeSubtaskById(0);
        fileBackedTasksManager.save();
        fileBackedTasksManager.getPrioritizedTasks();
        fileBackedTasksManager.validator();

    }


}


