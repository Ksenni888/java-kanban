package repositories;
import models.Epic;
import models.Subtask;
import models.Task;
import services.InMemoryHistoryManager;
import services.TaskManager;
import java.util.ArrayList;
import java.util.List;

public class Managers {
  public static List<Task> getDefaultHistory(){
     System.out.println("История задач из Managers:" + InMemoryHistoryManager.viewTask);
       return InMemoryHistoryManager.viewTask;
   }
  public static TaskManager getDefault(){
          return new TaskManager() {
            @Override
            public void printListSubtaskIdEpic(int id) {

            }

            @Override
            public void printAllTask() {

            }

            @Override
            public ArrayList<Task> getAllTasks() {
                return null;
            }

            @Override
            public void deleteAllTasks() {

            }

            @Override
            public void deleteAllTask() {

            }

            @Override
            public void deleteAllEpics() {

            }

            @Override
            public void deleteAllSubtask() {

            }

            @Override
            public void saveTask(Task task) {

            }

            @Override
            public void saveSubtask(Subtask subtask) {

            }

            @Override
            public void saveEpic(Epic epic) {

            }

            @Override
            public void removeTaskById(int id) {

            }

            @Override
            public void removeEpicById(int id) {

            }

            @Override
            public void removeSubtaskById(int id) {

            }

            @Override
            public void changeStatusTask(int id, Task.Status status) {

            }

            @Override
            public void changeStatusSubtask(int id, Task.Status status) {

            }

            @Override
            public void changeStatusEpic(int id) {

            }

            @Override
            public ArrayList<Task> getAllTasks2() {
                return null;
            }

            @Override
            public ArrayList<Task> getAllSubtasks() {
                return null;
            }

            @Override
            public ArrayList<Task> getAllEpics() {
                return null;
            }

            @Override
            public Task getTaskById(int id) {
                return null;
            }

            @Override
            public Task getEpicById(int id) {
                return null;
            }

            @Override
            public Task getSubtaskById(int id) {
                return null;
            }

            @Override
            public ArrayList<Subtask> getListSubtask(int id) {
                return null;
            }

            };
    }
}

