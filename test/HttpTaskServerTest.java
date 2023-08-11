import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import http.HttpTaskManager;
import http.KVServer;
import models.Epic;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.Managers;
import services.InMemoryTaskManager;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static repositories.Managers.getGson;

class HttpTaskServerTest extends Tests.TaskManagerTest<HttpTaskManager> {


    private final InMemoryTaskManager inMemoryTaskManager = Managers.getDefault();

    private KVServer kvServer;

    HttpTaskServerTest() throws IOException, InterruptedException {
    }

    @BeforeEach
    protected void createKVServer() throws Exception {
        kvServer = new KVServer();
        kvServer.start();
        HttpTaskManager taskManager = createTaskManager();
    }

    @AfterEach
    void stopKvServer() {
        kvServer.stop();
    }

    private HttpTaskManager createTaskManager() throws IOException, InterruptedException {
        HttpTaskManager httpTaskManager = new HttpTaskManager("http://localhost:8078");
        httpTaskManager.load();
        return httpTaskManager;
    }

    @Test
    void shouldGetTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task = new Task()
                .setId(1)
                .setName("Позвонить подруге")
                .setDescription("Description")
                .setStatus(models.Status.IN_PROGRESS);
        task.setDuration(25);
        task.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 0, 0, 0));
        inMemoryTaskManager.tasksRepository.save(task);
        inMemoryTaskManager.getAllTasks().add(task);
        Gson gson = getGson();
        String json = gson.toJson(task);
        System.out.println(task);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            JsonArray arrayTasks = JsonParser.parseString(response.body()).getAsJsonArray();
            assertEquals(1, arrayTasks.size());
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldGetAllTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            JsonArray arrayTasks = JsonParser.parseString(response.body()).getAsJsonArray();
            assertEquals(1, arrayTasks.size());
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldGetSubtasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        Subtask subtask9 = new Subtask();
        subtask9.setId(3);
        subtask9.setName("Вымыть полы");
        subtask9.setDescription("Вымыть полы");
        subtask9.setStatus(models.Status.NEW);
        subtask9.setEpicID(6);
        subtask9.setStartTime(LocalDateTime.of(2020, 1, 2, 12, 10, 0, 0));
        subtask9.setDuration(10);
        inMemoryTaskManager.saveSubtask(subtask9);
        inMemoryTaskManager.getAllTasks().add(subtask9);

        Gson gson = getGson();
        String json = gson.toJson(subtask9);
        System.out.println(subtask9);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            JsonArray arraySubtask = JsonParser.parseString(response.body()).getAsJsonArray();
            assertEquals(1, arraySubtask.size());
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldGetEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic6 = new Epic();
        epic6.setId(2);
        epic6.setName("Сделать уборку");
        epic6.setDescription("Сделать уборку");
        epic6.setStatus(models.Status.IN_PROGRESS);
        inMemoryTaskManager.saveEpic(epic6);
        inMemoryTaskManager.getAllTasks().add(epic6);

        Gson gson = getGson();
        String json = gson.toJson(epic6);
        System.out.println(epic6);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            JsonArray arrayEpics = JsonParser.parseString(response.body()).getAsJsonArray();
            assertEquals(1, arrayEpics.size());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    void shouldGetHistory() throws IOException, InterruptedException {
        HttpClient client1 = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task?id=1");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpClient client = HttpClient.newHttpClient();
            URI url = URI.create("http://localhost:8080/tasks/history");

            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            JsonArray arrayHistory = JsonParser.parseString(response.body()).getAsJsonArray();
            System.out.println(arrayHistory);
            assertEquals(1, arrayHistory.size());
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}