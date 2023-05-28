package repositories;
import java.util.ArrayList;
import java.util.HashMap;

public class EpicRepository {
    HashMap<Integer,Epic> epics = new HashMap<>();


public void save(Epic epic){ //сохранение эпика (перезапись эпика по id)
   epics.put(epic.getId(), epic);
}

    public ArrayList<Task> getAll() { //список всех эпиков
        ArrayList<Task> EpicsFromHash = new ArrayList<>();
        for (Task tas : epics.values()) {
            EpicsFromHash.add(tas);}
        return EpicsFromHash;
    }

    public void deleteAll(){ //удалить все эпики из таблицы
        epics.clear();

    }
    public Task get(int id){
        return epics.get(id);
    } //получить id эпика
    public void removeById(int id) {
        epics.remove(id);
    } //удалить эпик из таблицы по id



    }





