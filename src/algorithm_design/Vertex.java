package algorithm_design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vertex<T> {
    List<Vertex<T>> vertices;
    Map<Vertex<T>, Integer> weight;
    T data;
    int id;

    Vertex(T data) {
        this(data.hashCode(), data);
    }

    Vertex(int id, T data) {
        this.id = id;
        this.data = data;
        vertices = new ArrayList<>();
        weight = new HashMap<>();
    }

    public int degree() {
        return vertices.size();
    }

    public void addVertex(Vertex<T> vertex) {
        this.addVertex(vertex, 1);
    }

    public void addVertex(Vertex<T> vertex, int weight) {
        if (!this.contains(vertex)) {
            this.vertices.add(vertex);
            this.weight.put(vertex, weight);
        }
    }

    public void addVertex(int id, T data) {
        Vertex<T> vertex = new Vertex<>(id, data);
        this.addVertex(vertex);
    }

    public void addVertex(T data) {
        Vertex<T> vertex = new Vertex<>(data);
        this.addVertex(vertex);
    }

    public void addVertex(int id, T data, int weight) {
        Vertex<T> vertex = new Vertex<>(id, data);
        this.addVertex(vertex, weight);
    }

    public void addVertex(T data, int weight) {
        Vertex<T> vertex = new Vertex<>(data);
        this.addVertex(vertex, weight);
    }

    public boolean contains(Vertex vertex) {
        return this.vertices.indexOf(vertex) != -1;
    }
}
