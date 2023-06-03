package services;
import models.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    public static List<Task> viewTask = new ArrayList<>();
    private List<Task> pointTask = new ArrayList<>(); //список всех просмотренных задач

    @Override
    public void add(Task task){ //метод, формирующий список всех просмотренных задач
        pointTask.add(task);
    }

    @Override   //история просмотра последних 10 задач
    public List<Task> getHistory(int id, Task task){
        if  (viewTask.size()>9){viewTask.remove(0); viewTask.add(task);}
        else { viewTask.add(task);}
        System.out.println("История просмотренных задач из InMemoryHistoryManager: " + viewTask); //история просмотров задач, вызываемых по id
        return viewTask;
    }


}
