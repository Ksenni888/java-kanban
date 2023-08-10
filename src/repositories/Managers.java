package repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.HttpTaskManager;
import services.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Managers {


    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HttpTaskManager getDefaultHttpManager() throws IOException, InterruptedException {
        HttpTaskManager httpTaskManager = new HttpTaskManager("http://localhost:8078");
        httpTaskManager.load();
        return httpTaskManager;
    }

    public static FileBackedTasksManager getBackedTaskManager(File file) {
        return FileBackedTasksManager.loadFromFile(file);
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, GsonAdapters.localDateTime().nullSafe())
                .registerTypeAdapter(Duration.class, GsonAdapters.duration().nullSafe());
        return gsonBuilder.create();
    }

}



