package http;

import com.sun.net.httpserver.HttpServer;
import http.handlers.*;
import services.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final HttpServer httpServer;
    private static final int PORT = 8080;

    public HttpTaskServer() throws IOException, InterruptedException {

        TaskManager taskManager = new HttpTaskManager("http://localhost:8078");

        this.httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler(taskManager)); //обычные задачи
        httpServer.createContext("/tasks/subtask", new SubtaskHandler(taskManager)); //для подзадач
        httpServer.createContext("/tasks/epic", new EpicHandler(taskManager));
        httpServer.createContext("/tasks", new AllTasksHandler(taskManager));//получить все задачи
        httpServer.createContext("/tasks/history", new HistoryHandler(taskManager));
        System.out.println("Запускаем сервер на порту " + PORT);
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }
}





