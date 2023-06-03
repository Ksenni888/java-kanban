package services;
import models.Task;
import java.util.List;

interface HistoryManager {

    void add(Task task); //должен помечать задачи как просмотренные

    List<Task> getHistory(int id, Task task); //возвращает список просмотренных задач
}
