package services;

import models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

    static public HashMap<Integer, Node<Task>> historyHash = new HashMap<>();

    public class Node<E> {

        public E getData() { //создала геттер
            return data;
        }

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
        if (node != null) {
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
    }


    private static ArrayList<Task> historyTasksFinal = new ArrayList<>();

    public void setHis2(ArrayList<Task> his2) {

        this.historyTasksFinal = his2;

    }

    public ArrayList<Task> getHis2() {
        return historyTasksFinal;
    }

    public Node<Task> getHead() { //создала геттер
        return head;
    }

    public ArrayList<Task> getTasks() { //собирает все задачи из двусвязного в обычный ArrayList
        ArrayList<Task> historyTasks = new ArrayList<>();
        Node<Task> newNode = head;
        while (newNode != null) {
            if (!historyTasks.contains(newNode.data)) {
                historyTasks.add(newNode.data);
                setHis2(historyTasks);
            }
            newNode = newNode.next;
        }
        return historyTasks;
    }

    public HashMap<Integer, Node<Task>> getHistoryHash() {
        return historyHash;
    }

    @Override //удаление задачи из двусвязного списка истории просмотров
    public void remove(Task task) {
        removeNode(historyHash.get(task.getId()));
        historyHash.remove(task.getId());
        historyTasksFinal.remove(task);


    }

    @Override
    public void add(Task task) { //метод, формирующий список всех просмотренных задач
        if (task != null) {
            if (historyHash.containsKey(task.getId())) {
                historyHash.put(task.getId(), linkLast(task));
            } else {
                historyHash.put(task.getId(), linkLast(task));
            }
            getTasks();
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyTasksFinal;
    }

}
