package services;
import models.Task;
import java.util.HashMap;
import java.util.List;

public interface HistoryManager {

    void add(Task task); //должен помечать задачи как просмотренные
    void remove(int id);
    List<Task> getHistory(); //возвращает список просмотренных задач
    HashMap<Integer, InMemoryHistoryManager.Node<Task>> getHistoryHash();
}
