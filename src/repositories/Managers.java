package repositories;

import http.HttpTaskManager;
import http.KVServer;
import services.HistoryManager;
import services.InMemoryHistoryManager;
import services.InMemoryTaskManager;
import services.TaskManager;

import java.io.File;
import java.io.IOException;


public class Managers {

    public static TaskManager getInMemoryTaskManager(HistoryManager historyManager) {
        return new InMemoryTaskManager();
    }

    public static HttpTaskManager getDefault1(HistoryManager historyManager) throws IOException, InterruptedException {
        return new HttpTaskManager( "http://localhost:" + KVServer.PORT);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


public static FileBackedTasksManager getDefault() {
       File file = new File("file.csv");
        return new FileBackedTasksManager(file);

    }

   // public static services.HistoryManager getDefaultHistory() {
   //     return new InMemoryHistoryManager();
   // }


}



