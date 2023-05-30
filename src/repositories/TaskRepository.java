package repositories;
import models.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskRepository {
    private HashMap<Integer, Task> tasks = new HashMap<>();

    public void save(Task task) {tasks.put(task.getId(), task); } //сохранение новой задачи в таблице (замена задачи)
    public void deleteAll() { tasks.clear(); } //удаление всех задач из таблицы
    public void removeById(int id) { tasks.remove(id); } //удаление задачи по id
    public void changeStatus(int id, String newStatus){ //смена статуса конкретной задачи по id
        for (Task task: tasks.values()) {
            if (task.getId() == id){
                task.setStatus(newStatus);
            }
        }
    }

    public ArrayList<Task> getAll() { //список всех задач
        ArrayList<Task> taskFromHash = new ArrayList<>();
        for (Task tas : tasks.values()) {
            taskFromHash.add(tas);
        }
        return taskFromHash;
    }

    public Task get(int id) { return tasks.get(id); } //получить id задачи




}
