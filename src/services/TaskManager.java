package services;

import models.Epic;
import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.List;

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

    ArrayList<Task> getAllTasks(); //заполнение списка всех задач

    ArrayList<Task> getAllTasks2(); //получить список всех задач

    ArrayList<Task> getAllSubtasks(); //получить список всех подзадач

    ArrayList<Task> getAllEpics(); //получить список всех эпиков

    Task getTaskById(int id); //получить задачу по id

    Task getEpicById(int id); //получить эпик по id

    Task getSubtaskById(int id); //получить подзадачу id

    ArrayList<Subtask> getListSubtask(int id); //получить список подзадач эпика по id

    List<Task> getHistory(int id, Task task);
}







