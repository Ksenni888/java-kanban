package http.handlers;
import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Status;
import models.Task;
import repositories.FileBackedTasksManager;
import repositories.Managers;
import services.TaskManager;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
 FileBackedTasksManager fileBackedTasksManager = Managers.getDefault();
    int rCode;

    String response;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        rCode = 200;

        if (httpExchange.getRequestMethod().equals("DELETE")) {
            System.out.println("Началась обработка /tasks/task запроса от клиента, метод DELETE");
            String query = httpExchange.getRequestURI().getQuery();
            if (query == null) {
                taskManager.deleteAllTask();
                response = "Все задачи удалены";
                rCode = 200;
            } else {
                try {
                    int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Task task = taskManager.getTaskById(id);

                    if (task != null) {
                        taskManager.removeTaskById(id);
                        response = "Задача id=" + id + " удалена";
                    } else {
                        response = "Задача с id=" + id + " не найдена";
                    }
                    rCode = 200;


                } catch (StringIndexOutOfBoundsException e) {
                    rCode = 400;
                    response = "В запросе нет id";
                } catch (NumberFormatException e) {
                    rCode = 400;
                    response = "Неверный формат id";
                }
            }

        } else if (httpExchange.getRequestMethod().equals("POST")) {
            System.out.println("Началась обработка /tasks/task запроса от клиента, метод POST");
            try{  String query = httpExchange.getRequestURI().getQuery();
                if (query == null){
                  String bodyRequest = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    JsonObject object = JsonParser.parseString(bodyRequest).getAsJsonObject();
                    Task task = new Task();
                    task.setId(object.get("id").getAsInt());
                    task.setName(object.get("name").getAsString());
                    task.setDescription(object.get("description").getAsString());
                    task.setDuration(object.get("duration").getAsInt());
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    task.setStartTime(LocalDateTime.parse(object.get("startTime").getAsString(),format));
                    task.setEndTime(LocalDateTime.parse(object.get("endTime").getAsString(),format));
                    task.setStatus(Status.valueOf(object.get("status").getAsString()));
                    taskManager.saveTask(task);
                    response = "Создана задача с id=" + object.get("id").getAsInt();
                    }
          else if (query != null) {
                int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
               Task task = taskManager.getTaskById(id);
                if (task != null) {
                  String bodyRequest = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                  task.setStatus(Status.valueOf(bodyRequest));
                    response = "Статус задачи изменен";
                }else {
                    response = "Задача с id=" + id + " не найдена";
                }}


            } catch(StringIndexOutOfBoundsException e){
            rCode = 400;
            response = "В запросе нет id";
        } catch(NumberFormatException e){
            rCode = 400;
            response = "Неверный формат id";
        }

}
             else if (httpExchange.getRequestMethod().equals("GET")){
            System.out.println("Началась обработка /tasks/task запроса от клиента, метод GET");
            String query = httpExchange.getRequestURI().getQuery();
            if (query == null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                String json = gson.toJson("Все задачи: " + taskManager.getAllTasks2());
                response = json;

                rCode = 200;
            }

            else if (query != null){
                try{ int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Task task = taskManager.getTaskById(id);
                    if (task != null) {
                        response = "Задача по id: " + task;
                    } else {
                        response = "Задача с id=" +id+" не найдена";
                    }
                    rCode = 200;


                } catch (StringIndexOutOfBoundsException e) {
                    rCode = 400;
                    response = "В запросе нет id";
                } catch (NumberFormatException e) {
                    rCode = 400;
                    response = "Неверный формат id";
                }
            }
            else {
                response = "Некорректный запрос";
            }

        }
            httpExchange.sendResponseHeaders(rCode, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
