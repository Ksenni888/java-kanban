import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import http.HttpTaskManager;
import http.HttpTaskServer;
import http.KVServer;
import http.KVTaskClient;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import repositories.FileBackedTasksManager;
import repositories.Managers;
import services.HistoryManager;
import services.InMemoryHistoryManager;
import services.InMemoryTaskManager;
import services.TaskManager;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        KVServer server = new KVServer();
        server.start();
        Gson gson = new GsonBuilder().create();
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
    }
}

//!!!!!!Сейчас тут только происходит загрузка данных из файла через fileBackedTasksManager!!!!!
    /*   FileBackedTasksManager fileBackedTasksManager = (FileBackedTasksManager) Managers.getDefault();
      File file = new File("file.csv");
      fileBackedTasksManager.loadFromFile(file);
     HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.HttpTaskServer();
      //  KVServer kvServer = new KVServer();
        Gson gson = new Gson();
       KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8087");
        new KVServer().start();*/
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
        //  manager.removeEpicById(11)*/