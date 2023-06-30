package services;

import models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;
    protected static List<Task> viewTask = new ArrayList<>();
    public HashMap<Integer, Node<Task>> historyHash = new HashMap<>();

    class Node<E> {

        private E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(E data) {
            this(null, data, null);
        }

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

    }

    public Node<Task> linkLast(Task task) { //будет добавлять задачу в конец двусвязного списка
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else oldTail.next = newNode;
        size++;
        return newNode;

    }

    public void removeNode(Node<Task> node) {
        final Task task = node.data;
        final Node<Task> next = node.next;
        final Node<Task> prev = node.prev;

        if (node.prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (node.next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.data = null;
        size--;
    }

    public ArrayList<Task> getTasks() { //собирает все задачи из двусвязного в обычный ArrayList
        ArrayList<Task> historyTasks = new ArrayList<>();
        Node<Task> newNode = head;
        while (newNode != null) {
            historyTasks.add(newNode.data);
            newNode = newNode.next;
        }
        System.out.println("История просмотра задач: " + historyTasks);
        return historyTasks;
    }

    public HashMap<Integer, Node<Task>> getHistoryHash() {
        return historyHash;
    }

    @Override //удаление задачи из двусвязного списка истории просмотров
    public void remove(int id) {
        removeNode(historyHash.get(id));
    }

    @Override
    public void add(Task task) { //метод, формирующий список всех просмотренных задач
        if (historyHash.containsKey(task.getId())) {
            remove(task.getId());
            getTasks();
        }
        historyHash.put(task.getId(), linkLast(task));
        getTasks();
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

}
