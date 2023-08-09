import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import http.HttpTaskManager;
import http.HttpTaskServer;
import http.KVServer;
import http.KVTaskClient;
import models.Epic;
import models.Status;
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
  //  FileBackedTasksManager fileBackedTasksManager = (FileBackedTasksManager) Managers.getDefault();
   //  File file = new File("file.csv");
 //  fileBackedTasksManager.loadFromFile(file);
       // HistoryManager historyManager = Managers.getDefaultHistory();
      ///  TaskManager httpTaskManager = Managers.getDefault1(historyManager);
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.httpTS();
        KVServer kvServer = new KVServer();
        kvServer.start();

        //httpTaskServer.start();
       Gson gson = new Gson();

        Task task1 = new Task(  );





   /*     HttpClient clientTasks = HttpClient.newHttpClient(); // Получить все задачи
        URI url = URI.create("http://localhost:8080/tasks/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build(); //описание запроса, куда
      HttpResponse<String> response = clientTasks.send(request, HttpResponse.BodyHandlers.ofString()); //ответ сервера
     System.out.println("Код ответа: " + response.statusCode());
     System.out.println("Тело ответа: " + response.body());*/

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
        //  manager.removeEpicById(11);
   */
      // fileBackedTasksManager.getPrioritizedTasks();
     //  fileBackedTasksManager.validator();


   /*     try {

            KVServer server = new KVServer();
            server.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            TaskManager httpTaskManager = Managers.getDefault1(historyManager);

            Task task6 = new Task()
                    .setId(1)
                    .setName("Отвести ребенка в садик")
                    .setDescription("Отвести ребенка в садик")
                    .setStatus(models.Status.IN_PROGRESS);
            task6.setDuration(2);
            task6.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
            InMemoryTaskManager.tasksRepository.save(task6);
            //InMemoryTaskManager.getAllTasks().add(task6);
            httpTaskManager.saveTask(task6);




      /*      Epic epic1 = new Epic(
                    "Посадить дерево",
                    "Дерево",
                    Status.NEW,
                    Instant.now(),
                    2
            );
            httpTaskManager.createEpic(epic1);

            Subtask subtask1 = new Subtask(
                    "Купить семена",
                    "Семена",
                    Status.NEW,
                    epic1.getId(),
                    Instant.now(),
                    3
            );
         //   httpTaskManager.createSubtask(subtask1);


      //      httpTaskManager.getTaskById(task6.getId());
          //  httpTaskManager.getEpicById(epic1.getId());
          //  httpTaskManager.getSubtaskById(subtask1.getId());

     //       System.out.println("Печать всех задач");
            Gson gson = new Gson();
            System.out.println(gson.toJson(httpTaskManager.getAllTasks()));
           System.out.println("Печать всех эпиков");
            System.out.println(gson.toJson(httpTaskManager.getAllEpics()));
            System.out.println("Печать всех подзадач");
            System.out.println(gson.toJson(httpTaskManager.getAllSubtasks()));
            System.out.println("Загруженный менеджер");
            System.out.println(httpTaskManager);
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    }

