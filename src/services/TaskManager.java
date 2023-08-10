package services;

import models.Epic;
import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    void printListSubtaskIdEpic(int id); //печатаем подзадачи эпика по нужному id

    void printAllTask(); //печать всех задач (задачи+эпики+подзадачи)

    void deleteAllTasks(); //удаление вообще всех задач+эпиков+подзадач из общего списка

    void deleteAllTask();//удаление всех задач из таблицы задач

    void deleteAllEpics(); //удаление всех эпиков из таблицы эпиков

    void deleteAllSubtask(); //удаление всех подзадач из таблицы подзадач

    void saveTask(Task task);//сохранение задач в таблицу

    void saveSubtask(Subtask subtask); //сохранение подзадач в таблицу

    void saveEpic(Epic epic); //сохранение эпиков в таблицу

    void removeTaskById(int id); //удаление задачи по id

    void removeEpicById(int id); //удаление эпика по id

    void removeSubtaskById(int id); //удаление подзадачи по id

    void changeStatusTask(int id, models.Status status); //сменить статус задачи по id

    void changeStatusSubtask(int id, models.Status status); //сменить статус подзадачи по id

    void changeStatusEpic(int id); //изменить статус заданного эпика

    List<Task> getAllallTasks(); //получить список всех задач


    List<Task> getAllTasks(); //получить список всех задач

   List<Subtask> getAllSubtasks(); //получить список всех подзадач

    List<Epic> getAllEpics(); //получить список всех эпиков

    Task getTaskById(int id); //получить задачу по id

    Task getEpicById(int id); //получить эпик по id

    Task getSubtaskById(int id); //получить подзадачу id

    List<Subtask> getListSubtask(int id); //получить список подзадач эпика по id

     List<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();
}







