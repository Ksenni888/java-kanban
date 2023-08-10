package http;

import com.google.gson.*;
import models.Epic;
import models.Subtask;
import models.Task;
import repositories.FileBackedTasksManager;
import repositories.Managers;

import java.io.IOException;
import java.util.stream.Collectors;


public class HttpTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient;
    private final Gson gson;


    public HttpTaskManager(String url) throws IOException, InterruptedException {
        super();
        kvTaskClient = new KVTaskClient(url);
        kvTaskClient.register(url);
        gson = Managers.getGson();
    }


    @Override
    public void save() {

        kvTaskClient.put("tasks", gson.toJson(tasksRepository.getTasks().values()));//

        kvTaskClient.put("subtasks", gson.toJson(subtaskRepository.getSubtasks().values()));
        kvTaskClient.put("epics", gson.toJson(epicRepository.getEpics().values()));
        kvTaskClient.put("history", gson.toJson(getHistory()
                .stream()
                .map(Task::getId)
                .collect(Collectors.toList())));
    }

    @Override
    public void load() {
        JsonElement jsonTasks = JsonParser.parseString(kvTaskClient.load("tasks"));
        if (!jsonTasks.isJsonNull()) {
            JsonArray jsonTasksArray = jsonTasks.getAsJsonArray();
            for (JsonElement jsonTask : jsonTasksArray) {
                Task task = gson.fromJson(jsonTask, Task.class);
                tasksRepository.save(task);
            }
        }

        JsonElement jsonEpics = JsonParser.parseString(kvTaskClient.load("epics"));
        if (!jsonEpics.isJsonNull()) {
            JsonArray jsonEpicsArray = jsonEpics.getAsJsonArray();
            for (JsonElement jsonEpic : jsonEpicsArray) {
                Epic epic = gson.fromJson(jsonEpic, Epic.class);
                epicRepository.save(epic);
            }
        }

        JsonElement jsonSubtasks = JsonParser.parseString(kvTaskClient.load("subtasks"));
        if (!jsonSubtasks.isJsonNull()) {
            JsonArray jsonSubtasksArray = jsonSubtasks.getAsJsonArray();
            for (JsonElement jsonSubtask : jsonSubtasksArray) {
                Subtask subtask = gson.fromJson(jsonSubtask, Subtask.class);
                subtaskRepository.save(subtask);
            }
        }

        JsonElement jsonHistoryList = JsonParser.parseString(kvTaskClient.load("history"));
        if (!jsonHistoryList.isJsonNull()) {
            JsonArray jsonHistoryArray = jsonHistoryList.getAsJsonArray();
            for (JsonElement jsonTaskId : jsonHistoryArray) {
                int id = jsonTaskId.getAsInt();
                if (subtaskRepository.getSubtasks().containsKey(id)) {
                    this.getSubtaskById(id);
                } else if (epicRepository.getEpics().containsKey(id)) {
                    this.getEpicById(id);
                } else if (tasksRepository.getTasks().containsKey(id)) {
                    this.getTaskById(id);
                }
            }
        }
    }

}
