package http.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import repositories.FileBackedTasksManager;
import services.TaskManager;

import java.io.IOException;
import java.io.OutputStream;

public class HistoryHandler implements HttpHandler {
    public HistoryHandler(FileBackedTasksManager fileBackedTasksManager) {
        this.fileBackedTasksManager = fileBackedTasksManager;
    }

    private final FileBackedTasksManager fileBackedTasksManager;
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        String response;
        if (httpExchange.getRequestMethod().equals("GET")) {
            System.out.println("Началась обработка /history запроса от клиента, метод GET");
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            String json = gson.toJson("История задач: " + fileBackedTasksManager.getInMemoryHistoryManager().getHistoryHash().keySet());
            response = json;
            System.out.println("История задач: " + fileBackedTasksManager.getInMemoryHistoryManager().getHistoryHash().keySet());
        } else {
            response = "Некорректный запрос";
        }


        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

