package repositories;
import services.HistoryManager;
import models.Subtask;
import models.Task;
import services.InMemoryHistoryManager;
import services.InMemoryTaskManager;
import services.TaskManager;
import java.util.ArrayList;
import java.util.List;

public class Managers {
  public static TaskManager getDefault(){return new InMemoryTaskManager();
  }

  public static services.HistoryManager getDefaultHistory() {
    return new InMemoryHistoryManager();
  }
}



