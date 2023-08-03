package repositories;

import services.InMemoryHistoryManager;
import services.TaskManager;

import java.io.File;


public class Managers {

    public static TaskManager getDefault() {
        File file = new File("file.csv");
        return new FileBackedTasksManager(file);
    }

    public static services.HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}



