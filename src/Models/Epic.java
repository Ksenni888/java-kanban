package models;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> idSubtask = new ArrayList<>();

    public ArrayList<Integer> getIdSubtask() {
        return idSubtask;
    }

    public void setIdSubtask(int id) {
        idSubtask.add(id);
    }
}
