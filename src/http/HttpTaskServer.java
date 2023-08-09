package http;
import com.sun.net.httpserver.HttpServer;
import http.handlers.*;
import repositories.FileBackedTasksManager;
import repositories.Managers;

import services.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
  private HttpServer httpServer;
  private static final int PORT = 8080;

     public void httpTS() throws IOException, InterruptedException {
    FileBackedTasksManager fileBackedTasksManager = Managers.getDefault();

       httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT),0);
        httpServer.createContext("/tasks/task", new TaskHandler(fileBackedTasksManager)); //обычные задачи
        httpServer.createContext("/tasks/subtask", new SubtaskHandler(fileBackedTasksManager)); //для подзадач
        httpServer.createContext("/tasks/epic", new EpicHandler(fileBackedTasksManager));
        httpServer.createContext("/tasks", new AllTasksHandler(fileBackedTasksManager));//получить все задачи
        httpServer.createContext("/tasks/history", new HistoryHandler(fileBackedTasksManager));
        System.out.println("Запускаем сервер на порту "+PORT);
        httpServer.start();}

 }



 /* HttpClient clientTasks = HttpClient.newHttpClient(); // Получить все задачи
  URI url0 = URI.create("http://localhost:8080/tasks/");
  HttpRequest request0 = HttpRequest.newBuilder().uri(url0).GET().build(); //описание запроса, куда
 // HttpResponse<String> response0 = clientTasks.send(request0, HttpResponse.BodyHandlers.ofString()); //ответ сервера
//  System.out.println("Код ответа: " + response0.statusCode());
//  System.out.println("Тело ответа: " + response0.body());

   /*     HttpClient clientTask = HttpClient.newHttpClient(); // Получить задачи
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build(); //описание запроса, куда
        HttpResponse<String> response = clientTask.send(request, HttpResponse.BodyHandlers.ofString()); //ответ сервера
     //  System.out.println("Код ответа: " + response.statusCode());
    //   System.out.println("Тело ответа: " + response.body());

        HttpClient clientTaskCreate = HttpClient.newHttpClient(); //Создать задачу
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        Gson gson = new Gson();
        String json = gson.toJson(  ""  );//id,type,name,status,description,epic,startTime,duration,endTime
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).POST(body).build();
        HttpResponse<String> response1 = clientTaskCreate.send(request1, HttpResponse.BodyHandlers.ofString());
      //  System.out.println("Код ответа: " + response1.statusCode());
     //   System.out.println("Тело ответа: " + response1.body());

        HttpClient clientGetTaskId = HttpClient.newHttpClient(); //получить задачу с id = 1
        URI url2 = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();
        HttpResponse<String> response2 = clientGetTaskId.send(request2, HttpResponse.BodyHandlers.ofString());
      //  System.out.println("Код ответа: " + response2.statusCode());
     //   System.out.println("Тело ответа: " + response2.body());
*/


  /*  public void start() {
        httpServer.start();
        System.out.println("HTTP-сервер запущен на "+PORT+" порту.");
    }*/

/*public void start() {
  httpServer.start();
 }

 public void stop() {
  httpServer.stop(2);
 }
        /*
        Подсказка: как работает сервер KVServer
        KVServer — это хранилище, где данные хранятся по принципу <ключ-значение>. Он умеет:
        GET /register — регистрировать клиента и выдавать уникальный токен доступа (аутентификации). Это нужно, чтобы хранилище могло работать сразу с несколькими клиентами.
        POST /save/<ключ>?API_TOKEN= — сохранять содержимое тела запроса, привязанное к ключу.
        GET /load/<ключ>?API_TOKEN= — возвращать сохранённые значение по ключу.

        Подсказка: как остановить KVServer
        Если запускать новый сервер перед каждым тестом на том же порту, то потребуется остановить предыдущий.
        Для этого реализуйте метод stop() в KVServer. Его вызов поместите в отдельный метод в тестах. Пометьте его аннотацией @AfterEach.

        Проверить API можно несколькими способами.
        Через Insomnia.



         *
         *
         * Задачи передаются в теле запроса в формате JSON.
         * Идентификатор (id) задачи следует передавать параметром запроса (через вопросительный знак).
         *
         * В результате для каждого метода интерфейса TaskManager должен быть создан отдельный эндпоинт, который можно будет вызвать по HTTP.
         * */

  //  }



