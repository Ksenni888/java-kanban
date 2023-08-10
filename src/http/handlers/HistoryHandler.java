package http.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import repositories.Managers;
import services.HistoryManager;
import services.TaskManager;

import java.io.IOException;
import java.io.OutputStream;

public class HistoryHandler implements HttpHandler {

    private final TaskManager taskManager;

    public HistoryHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    HistoryManager historyManager = Managers.getDefaultHistoryManager();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response;
        if (httpExchange.getRequestMethod().equals("GET")) {
            System.out.println("Началась обработка /history запроса от клиента, метод GET");
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            String json = gson.toJson("История задач: " + historyManager.getHistory());
            System.out.println("Началась обработка /history запроса от клиента, метод GET11");
            response = json;
            System.out.println("История задач: " + historyManager.getHistory());
        } else {
            response = "Некорректный запрос";
        }


        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

