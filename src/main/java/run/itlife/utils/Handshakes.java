package run.itlife.utils;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Поиск в ширину (BFS - Breadth-First Search)
 */
public class Handshakes {
    /**
     * Граф
     */
    public Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
    /**
     * Очередь
     */
    public ArrayDeque<Integer> searchDeque = new ArrayDeque<>();
    /**
     * Найденные вершины
     */
    public ArrayList<Integer> searched = new ArrayList<>();
    /**
     * Таблица с родителями
     */
    public Map<Integer, ArrayList<Integer>> parents = new HashMap<>();
    /**
     * Размер пути
     */
    public int sizePath = 0;
    /**
     * Начальный узел
     */
    public Integer startPerson;
    public static final Byte LIMIT_SIZE_HANDSHAKE = 6;

    public ArrayList<Integer> search(Integer searchPerson) {
        Integer person = null;
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> childs = addChilds(this.startPerson, this.graph);
        this.parents.put(this.startPerson, childs);
        this.searchDeque = addToDeque(this.startPerson, this.graph);
        this.searched.add(this.startPerson);

        while (!this.searchDeque.isEmpty()) {
            person = this.searchDeque.pollFirst();
            if (!this.searched.contains(person)) {
                if (person.equals(searchPerson)) {
                    return buildPath(searchPerson);
                } else {
                    childs = new ArrayList<>();
                    childs = addChilds(person, this.graph);
                    this.searchDeque = addToDeque(person, this.graph);
                    this.parents.put(person, childs);
                    searched.add(person);
                }
            }
        }
        return path;
    }

    private ArrayList<Integer> addChilds(Integer person, Map<Integer, ArrayList<Integer>> graph) {
        ArrayList<Integer> childs = new ArrayList<>();
        if (graph.get(person) != null) {
            for (int i = 0; i < graph.get(person).size(); i++) {
                if (!graph.get(person).equals("") && !this.searched.contains(graph.get(person))) {
                    childs.add(graph.get(person).get(i));
                }
            }
        }
        return childs;
    }

    private ArrayDeque<Integer> addToDeque(Integer person, Map<Integer, ArrayList<Integer>> graph) {
        if (graph.get(person) != null) {
            for (int i = 0; i < graph.get(person).size(); i++) {
                if (!graph.get(person).equals("")) {
                    this.searchDeque.addLast(graph.get(person).get(i));
                }
            }
        }
        return this.searchDeque;
    }

    public ArrayList<Integer> buildPath(Integer search_person) { // TODO Метод кривой, есть косяки - доработать
        ArrayList<Integer> path = new ArrayList<>();
        AtomicInteger ai = new AtomicInteger();
        Integer par = null;

        for (Map.Entry<Integer, ArrayList<Integer>> entry4 : this.parents.entrySet()) {
            if (entry4.getKey().equals(this.startPerson)) {
                for (int i = 0; i < entry4.getValue().size(); i++) {
                    if (entry4.getValue().get(i).equals(search_person)) {
                        path.add(entry4.getKey());
                        return path;
                    }
                }
            }
        }

        for (Map.Entry<Integer, ArrayList<Integer>> entry : this.parents.entrySet()) {
            if (entry.getKey().equals(this.startPerson)) {
                continue;
            }
            if (entry.getValue().contains(search_person)) {
                par = entry.getKey();
                path.add(par);
            }
            for (Map.Entry<Integer, ArrayList<Integer>> entry2 : this.parents.entrySet()) {
                if (entry2.getKey().equals(this.startPerson)) {
                    continue;
                }
                if (entry2.getValue().contains(par)) {
                    par = entry2.getKey();
                    this.sizePath = ai.incrementAndGet();
                    path.add(par);
                }
            }
        }

        for (Map.Entry<Integer, ArrayList<Integer>> entry3 : this.parents.entrySet()) {
            if (entry3.getKey().equals(this.startPerson)) {
                par = entry3.getKey();
                this.sizePath = ai.incrementAndGet();
                path.add(par);
            }
        }

        if (path.size() == 1) {
            this.sizePath = ai.decrementAndGet(); // TODO Костыль
        }

        // В случае некорретного графа, сделаем обработку
        if (!path.contains(this.startPerson) || sizePath > LIMIT_SIZE_HANDSHAKE) {
            path.removeAll(path);
        }
        return path;
    }
}