package repositories;
import java.util.ArrayList;
import java.util.HashMap;

public class SubtaskRepository {
    HashMap<Integer,Subtask> subtasks = new HashMap<>();
    public void save(Subtask subtask){ //сохраняет подзадачи в таблицу
        subtasks.put(subtask.getId(),subtask);
    }

    public ArrayList<Task> getAll() { //вывести список всех подзадач
        ArrayList<Task> subtaskFromHash = new ArrayList<>();
        for (Task tas : subtasks.values()) {
            subtaskFromHash.add(tas);}
            return subtaskFromHash;
        }

    public void deleteAll(){ //удалить все подзадачи из таблицы
        subtasks.clear();
    }
    public Task get(int id){
        return subtasks.get(id);
    } //вернуть подзадачу по id

    public void removeById(int id) {
        subtasks.remove(id);
    } //удалить подзадачу по id

    public ArrayList<Subtask> getListSubtask(int id) { //вывести все задачи определенного эпика
        ArrayList<Subtask> getListSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicID() == id) {
                getListSubtasks.add(subtask);
            }
        }
        return getListSubtasks;
    }

    public void changeStatus(int id, String newStatus){ //присвоить новый статус подзадаче по id
        for (Subtask subtask: subtasks.values()) {
            if (subtask.getId() == id){
                subtask.setStatus(newStatus);
            }
        }
    }

}

