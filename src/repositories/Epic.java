package repositories;
import java.util.ArrayList;


public class Epic extends Task{


  private ArrayList<Subtask> subTasks = new ArrayList<>();
    public void addSubtask(Subtask subTask){
    subTasks.add(subTask);
}

}
