package repositories;

import models.Epic;
import models.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class EpicRepository {
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    private HashMap<Integer, Epic> epics = new HashMap<>();

    public void save(Epic epic) { //сохранение эпика (перезапись эпика по id)
        epics.put(epic.getId(), epic);
    } //сохранение всех эпиков в таблицу

    public void deleteAll() { epics.clear();  }  //удалить все эпики из таблицы

    public void removeById(int id) { //удалить эпик из таблицы по id
        epics.remove(id);
    }

    public ArrayList<Epic> getAll() { //список всех эпиков
        ArrayList<Epic> EpicsFromHash = new ArrayList<>();
        for (Epic tas : epics.values()) {
            EpicsFromHash.add(tas);
        }
        return EpicsFromHash;
    }

    public Epic get(int id) {
        return epics.get(id);
    }  //получить id эпика
}





