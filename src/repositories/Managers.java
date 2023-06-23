package repositories;
import services.InMemoryHistoryManager;
import services.InMemoryTaskManager;
import services.TaskManager;


public class Managers {
  public static TaskManager getDefault(){return new InMemoryTaskManager();
  }

  public static services.HistoryManager getDefaultHistory() {
    return new InMemoryHistoryManager();
  }
}



