package http.handlers;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Status;
import models.Subtask;
import models.Task;
import services.TaskManager;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubtaskHandler implements HttpHandler {
    private final TaskManager taskManager;

    public SubtaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    int rCode;
    String response;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        rCode = 200;

        if (httpExchange.getRequestMethod().equals("GET")) {
            System.out.println("Началась обработка /tasks/subtask запроса от клиента, метод GET");
            String query = httpExchange.getRequestURI().getQuery();
            if (query != null) {
                try {
                    int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Task subtask = taskManager.getSubtaskById(id);
                    if (subtask != null) {
                        response = "Подзадача по id: " + subtask;
                        System.out.println("Подзадача по id: " + subtask);
                    } else {
                        response = "Подзадача с id=" + id + " не найдена";
                    }
                    rCode = 200;


                } catch (StringIndexOutOfBoundsException e) {
                    rCode = 400;
                    response = "В запросе нет id";
                } catch (NumberFormatException e) {
                    rCode = 400;
                    response = "Неверный формат id";
                }
            } else if (query == null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                String json = gson.toJson("Все подзадачи: " + taskManager.getAllSubtasks());
                System.out.println("Сформирован список всех подзадач");
                response = json;
                rCode = 200;
            }

        }
        if (httpExchange.getRequestMethod().equals("POST")) {
            System.out.println("Началась обработка /task/subtask запроса от клиента, метод POST");
            try {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {

                    String bodyRequest = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    JsonObject object = JsonParser.parseString(bodyRequest).getAsJsonObject();
                    Subtask subtask = new Subtask();
                    subtask.setId(object.get("id").getAsInt());
                    subtask.setName(object.get("name").getAsString());
                    subtask.setDescription(object.get("description").getAsString());
                    subtask.setDuration(object.get("duration").getAsInt());
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    subtask.setStartTime(LocalDateTime.parse(object.get("startTime").getAsString(), format));
                    subtask.setEpicID(object.get("epicId").getAsInt());
                    subtask.setStatus(Status.valueOf(object.get("status").getAsString()));
                    taskManager.saveSubtask(subtask);
                    response = "Создана задача с id=" + object.get("id").getAsInt();
                    System.out.println("Добавлена новая задача c id=" + object.get("id").getAsInt());
                } else if (query != null) {
                    int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Subtask subtask = (Subtask) taskManager.getSubtaskById(id);
                    if (subtask != null) {
                        String bodyRequest = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                        subtask.setStatus(Status.valueOf(bodyRequest));
                        response = "Статус задачи изменен";
                        System.out.println("Статус задачи с id=" + id + " изменен на " + Status.valueOf(bodyRequest));
                    } else {
                        response = "Задача с id=" + id + " не найдена";
                    }
                }


            } catch (StringIndexOutOfBoundsException e) {
                rCode = 400;
                response = "В запросе нет id";
            } catch (NumberFormatException e) {
                rCode = 400;
                response = "Неверный формат id";
            }


        } else if (httpExchange.getRequestMethod().equals("DELETE")) {
            System.out.println("Началась обработка /task/subtask запроса от клиента, метод DELETE");
            String query = httpExchange.getRequestURI().getQuery();
            if (query == null) {
                taskManager.deleteAllSubtask();
                response = "Все подзадачи удалены";
                System.out.println("Все подзадачи удалены");
                rCode = 200;
            } else {
                try {
                    int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Subtask subtask = (Subtask) taskManager.getSubtaskById(id);

                    if (subtask != null) {
                        taskManager.removeSubtaskById(id);
                        response = "Подзадача id=" + id + " удалена";
                        System.out.println("Подзадача id=" + id + " удалена");
                    } else {
                        response = "Подзадача с id=" + id + " не найдена";
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

        }

        httpExchange.sendResponseHeaders(rCode, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }


}
