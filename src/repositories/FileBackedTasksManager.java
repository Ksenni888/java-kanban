package repositories;

import models.*;
import models.Enum;
import services.InMemoryHistoryManager;
import services.InMemoryTaskManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static services.InMemoryHistoryManager.historyHash;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    private Enum Enum;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    protected static InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    protected static InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    class ManagerSaveException extends Exception {
        public ManagerSaveException(final String message) {
            super(message);
        }

    }

    public void save() {

        try {
            if (!(file.getPath()).equals("file.csv")) {
                throw new ManagerSaveException("Неверный путь к файлу  " + file);
            }
            FileWriter fileWriter = new FileWriter(file);
            System.out.println("id,type,name,status,description,epic");
            fileWriter.write("id,type,name,status,description,epic\n");

            for (Task task2 : inMemoryTaskManager.getAllTasks2()) {
                System.out.println(task2.getId() + "," + Enum.TASK + "," + task2.getName() + "," + task2.getStatus() + "," + task2.getDescription());
                fileWriter.write(task2.getId() + "," + Enum.TASK + "," + task2.getName() + "," + task2.getStatus() + "," + task2.getDescription() + "," + task2.getId() + "\n");
            }

            for (Task epic3 : inMemoryTaskManager.getAllEpics()) {
                System.out.println(epic3.getId() + "," + Enum.EPIC + "," + epic3.getName() + "," + epic3.getStatus() + "," + epic3.getDescription());
                fileWriter.write(epic3.getId() + "," + Enum.EPIC + "," + epic3.getName() + "," + epic3.getStatus() + "," + epic3.getDescription() + "," + epic3.getId() + "\n");

            }

            for (Subtask subtask2 : inMemoryTaskManager.getAllSubtasks()) {
                System.out.println(subtask2.getId() + "," + Enum.SUBTASK + "," + subtask2.getName() + "," + subtask2.getStatus() + "," + subtask2.getDescription() + "," + subtask2.getEpicID());
                fileWriter.write(subtask2.getId() + "," + Enum.SUBTASK + "," + subtask2.getName() + "," + subtask2.getStatus() + "," + subtask2.getDescription() + "," + subtask2.getEpicID() + "\n");

            }
            fileWriter.write("\n");
            String result = getInMemoryHistoryManager().getHistoryHash().keySet().toString();
            fileWriter.write(result.substring(1, result.length() - 1));
            System.out.println("История просмотра: " + result.substring(1, result.length() - 1));
            fileWriter.close();
        } catch (ManagerSaveException | IOException e) {System.out.println(e.getMessage());
        }
    }


    public static void loadFromFile(File file) {
        TaskRepository tasks = new TaskRepository();
        SubtaskRepository subtaskRepository = new SubtaskRepository();
        EpicRepository epicRepository = new EpicRepository();
        try {
            String content = Files.readString(Paths.get(file.getPath()));
            String[] str = content.split("\n");
            for (int i = 1; i < str.length - 2; i++) {
                String str1 = str[i];
                String[] parts = str1.split(",");
                int id = Integer.parseInt(parts[0]);
                String type = parts[1];
                String name = parts[2];
                String status = parts[3];
                String description = parts[4];
                int epic = Integer.parseInt(parts[5]);
                if (type.equals("TASK")) {
                    Task task = new Task();
                    task.setId(id);
                    task.setName(name);
                    task.setStatus(Status.valueOf(status));
                    task.setDescription(description);
                    tasksRepository.save(task);
                }
                if (type.equals("EPIC")) {
                    Epic epics = new Epic();
                    epics.setId(id);
                    epics.setName(name);
                    epics.setStatus(Status.valueOf(status));
                    epics.setDescription(description);
                    epicRepository.save(epics);
                }
                if (type.equals("SUBTASK")) {
                    Subtask subtask = new Subtask();
                    subtask.setId(id);
                    subtask.setName(name);
                    subtask.setStatus(Status.valueOf(status));
                    subtask.setDescription(description);
                    subtask.setEpicID(epic);
                    subtaskRepository.save(subtask);
                }
            }
            System.out.println("Вывод задачи из файла: " + tasksRepository.getTasks());
            System.out.println("Вывод подзадач из файла: " + subtaskRepository.getSubtasks());
            System.out.println("Вывод эпиков из файла: " + epicRepository.getEpics());
            String[] str1 = str[str.length - 1].split(", ");
            for (int k = 0; k < str1.length; k++) {
                for (Task task : getAllTasks()) {
                    if (Integer.parseInt(str1[k]) == task.getId()) {
                        historyHash.put(task.getId(), inMemoryHistoryManager.linkLast(task));
                    }
                }
            }
            System.out.println("Задачи из истории: " + historyHash.keySet() + historyHash.values());

        } catch (IOException e) {
            System.out.println("Нет данных в файле");
        }
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
        InMemoryTaskManager.tasksRepository.save(task6);
        InMemoryTaskManager.allTasks.add(task6);

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
        subtask9.setStatus(models.Status.IN_PROGRESS);
        subtask9.setEpicID(epic6.getId());
        InMemoryTaskManager.subtaskRepository.save(subtask9);
        InMemoryTaskManager.allTasks.add(subtask9);

        Subtask subtask10 = new Subtask();
        subtask10.setId(4);
        epic6.setIdSubtask(subtask10.getId());
        subtask10.setName("Протереть пыль");
        subtask10.setDescription("Протереть пыль");
        subtask10.setStatus(models.Status.NEW);
        InMemoryTaskManager.subtaskRepository.save(subtask10);
        InMemoryTaskManager.allTasks.add(subtask10);

        System.out.println("Вывод эпика по заданному id: " + manager.getEpicById(2));
        System.out.println("Вывод подзадачи по заданному id: " + manager.getSubtaskById(4));
        fileBackedTasksManager.save();

    }


}


