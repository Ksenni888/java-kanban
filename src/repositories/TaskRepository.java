package repositories;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskRepository {


    public HashMap<Integer, Task> tasks = new HashMap<>();


    public void save(Task task) { //сохранение новой задачи в таблице (замена задачи)
        tasks.put(task.getId(), task);
            }

    public void deleteAll() {
        tasks.clear();
    } //удаление всех задач из таблицы

    public void removeById(int id) {
        tasks.remove(id);
    } //удаление задачи по id

    public ArrayList<Task> getAll() { //список всех задач
        ArrayList<Task> taskFromHash = new ArrayList<>();
        for (Task tas : tasks.values()) {
            taskFromHash.add(tas);
        }
        return taskFromHash;
    }

    public void changeStatus(int id, String newStatus){ //смена статуса конкретной задачи по id
        for (Task task: tasks.values()) {
            if (task.getId() == id){
                task.setStatus(newStatus);
            }
        }
    }

    public Task get(int id) { //получить id задачи
        return tasks.get(id);
    }



}
