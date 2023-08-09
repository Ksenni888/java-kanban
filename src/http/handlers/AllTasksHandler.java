package http.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import repositories.FileBackedTasksManager;
import services.TaskManager;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;



public class AllTasksHandler implements HttpHandler {
  //  private final TaskManager taskManager;

  //  public AllTasksHandler(TaskManager taskManager) {
    //    this.taskManager = taskManager;
 //   }

    public AllTasksHandler(FileBackedTasksManager fileBackedTasksManager) {
        this.fileBackedTasksManager = fileBackedTasksManager;
    }

   // File file = new File("file.csv");
   private final FileBackedTasksManager fileBackedTasksManager;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);

        String response;
        if (httpExchange.getRequestMethod().equals("GET")) {
            System.out.println("Началась обработка /tasks запроса от клиента, метод GET");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            String json = gson.toJson("Все задачи: " + fileBackedTasksManager.getPrioritizedTasks());
            response = json;
            System.out.println("Список всех задач готов");
        } else if(httpExchange.getRequestMethod().equals("DELETE")) {
            System.out.println("Началась обработка /tasks запроса от клиента, метод DELETE");
             fileBackedTasksManager.deleteAllTasks();
            response = "Все задачи удалены";
            System.out.println("Все задачи удалены");

        }

        else {
            response = "Некорректный запрос";
        }

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
