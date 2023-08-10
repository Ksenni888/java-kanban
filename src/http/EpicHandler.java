package http;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Epic;
import models.Status;
import services.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EpicHandler implements HttpHandler {
    private final TaskManager taskManager;
    int rCode;

    String response;

    public EpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        rCode = 200;
        System.out.println("Началась обработка /Epic запроса от клиента.");

        if (httpExchange.getRequestMethod().equals("DELETE")) {

            String query = httpExchange.getRequestURI().getQuery();
            if (query == null) {
                System.out.println("Началась обработка /Epic запроса от клиента, метод DELETE ALL EPICS");
                taskManager.deleteAllEpics();
                response = "Все эпики с подзадачами удалены";
                rCode = 200;
            } else {
                try {
                    int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Epic epic = (Epic) taskManager.getEpicById(id);

                    if (epic != null) {
                        taskManager.removeEpicById(id);
                        response = "Эпик id=" + id + " удален";
                    } else {
                        response = "Эпик с id=" + id + " не найден";
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
            try {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    String bodyRequest = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    JsonObject object = JsonParser.parseString(bodyRequest).getAsJsonObject();
                    Epic epic = new Epic();
                    epic.setId(object.get("id").getAsInt());
                    epic.setName(object.get("name").getAsString());
                    epic.setDescription(object.get("description").getAsString());
                    epic.setDurationEpic(object.get("duration").getAsInt());
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    epic.setStartTimeEpic((LocalDateTime.parse(object.get("startTime").getAsString(), format)));
                    epic.setEndTimeEpic((LocalDateTime.parse(object.get("endTime").getAsString(), format)));
                    epic.setStatus(Status.valueOf(object.get("status").getAsString()));
                    taskManager.saveEpic(epic);
                    response = "Создан эпик с id=" + object.get("id").getAsInt();
                } else if (query != null) {
                    int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Epic epic = (Epic) taskManager.getEpicById(id);
                    if (epic != null) {
                        taskManager.changeStatusEpic(epic.getId());
                        response = "Статус эпика изменен (на основании его подзадач)";
                    } else {
                        response = "Эпик с id=" + id + " не найден";
                    }
                }

            } catch (StringIndexOutOfBoundsException e) {
                rCode = 400;
                response = "В запросе нет id";
            } catch (NumberFormatException e) {
                rCode = 400;
                response = "Неверный формат id";
            }

        } else if (httpExchange.getRequestMethod().equals("GET")) {
            String query = httpExchange.getRequestURI().getQuery();
            if (query != null) {
                try {
                    int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Epic epic = (Epic) taskManager.getEpicById(id);
                    if (epic != null) {
                        Gson gson = new Gson();
                        String resp = gson.toJson(taskManager.getListSubtask(id).toString());


                        response = "Эпик по id: " + epic + "\n" + "подзадачи: " + resp;
                    } else {
                        response = "Эпик с id=" + id + " не найден";
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
                System.out.println("Получить все эпики");
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                String json = gson.toJson("Все эпики: " + taskManager.getAllEpics());
                response = json;
                rCode = 200;
            } else {
                response = "Некорректный запрос";
            }
        } else if (httpExchange.getRequestMethod().equals("OPTIONS")) {
            String query = httpExchange.getRequestURI().getQuery();
            if (query != null) {
                try {
                    int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                    Epic epic = (Epic) taskManager.getEpicById(id);
                    if (epic != null) {

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setPrettyPrinting();
                        Gson gson = gsonBuilder.create();
                        String json = gson.toJson("Все подзадачи эпика id=" + id + ": " + taskManager.getListSubtask(id));

                        response = json;
                    } else {
                        response = "Эпик с id=" + id + " не найден";
                    }
                    rCode = 200;


                } catch (StringIndexOutOfBoundsException e) {
                    rCode = 400;
                    response = "В запросе нет id";
                } catch (NumberFormatException e) {
                    rCode = 400;
                    response = "Неверный формат id";
                }
            } else {
                response = "Некорректный запрос";
            }

        }
        httpExchange.sendResponseHeaders(200, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
