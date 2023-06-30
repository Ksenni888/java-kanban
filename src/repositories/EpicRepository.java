package repositories;

import models.Epic;
import models.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class EpicRepository {
    private HashMap<Integer, Epic> epics = new HashMap<>();

    public void save(Epic epic) { //сохранение эпика (перезапись эпика по id)
        epics.put(epic.getId(), epic);
    } //сохранение всех эпиков в таблицу

    public void deleteAll() {
        epics.clear();
    }  //удалить все эпики из таблицы

    public void removeById(int id) { //удалить эпик из таблицы по id
        epics.remove(id);
    }

    public ArrayList<Task> getAll() { //список всех эпиков
        ArrayList<Task> EpicsFromHash = new ArrayList<>();
        for (Task tas : epics.values()) {
            EpicsFromHash.add(tas);
        }
        return EpicsFromHash;
    }

    public Task get(int id) {
        return epics.get(id);
    }  //получить id эпика
}





