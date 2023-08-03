package repositories;

import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.HashMap;


public class SubtaskRepository {
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public int getEpicId() {
        return epicId;
    }

    private static HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public void save(Subtask subtask) { //сохраняет подзадачи в таблицу
        subtasks.put(subtask.getId(), subtask);
        setIdEpic(subtask.getEpicID());
    }

    public void deleteAll() { //удалить все подзадачи из таблицы
        subtasks.clear();
    }

    public void removeById(int id) {
        subtasks.remove(id);
    } //удалить подзадачу по id

    public void changeStatus(int id, models.Status status) { //присвоить новый статус подзадаче по id
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getId() == id) {
                subtask.setStatus(status);
            }
        }
    }

    public ArrayList<Subtask> getListSubtask(int id) { //вывести все задачи определенного эпика
        ArrayList<Subtask> getListSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicID() == id) {
                getListSubtasks.add(subtask);
            }
        }
        return getListSubtasks;
    }

    public Task get(int id) {
        return subtasks.get(id);
    } //вернуть подзадачу по id

    private int epicId;//добавила

    public int getIdEpic() {//добавила
        return epicId;
    }

    public void setIdEpic(int epicId) {//добавила
        this.epicId = epicId;
    }

    public ArrayList<Subtask> getAll() { //вывести список всех подзадач
        ArrayList<Subtask> subtaskFromHash = new ArrayList<>();
        for (Subtask tas : subtasks.values()) {
            subtaskFromHash.add(tas);
        }
        return subtaskFromHash;
    }

}

