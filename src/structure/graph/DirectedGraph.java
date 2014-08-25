package structure.graph;

import java.util.Collection;
import java.util.Set;

/**
 * Created by boyce on 2014/8/22.
 */
public class DirectedGraph<T> extends AbstractGraph<T> {

    @Override
    public void addEdge(T element1, T element2) {
        this.addVertex(element1);
        this.addVertex(element2);

        this.vertexs.get(element1).addAdjVertex(element2);
        this.vertexs.get(element2).inDegree ++;
    }

    public void topSort() {
        Collection<Vertex<T>> vertexSet = this.vertexs.values();

    }

    public static void main(String[] args) {
        AbstractGraph<Integer> graph = new DirectedGraph<Integer>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);

        graph.addEdge(2, 4);
        graph.addEdge(2, 5);

        graph.addEdge(3, 6);

        graph.addEdge(4, 3);
        graph.addEdge(4, 6);
        graph.addEdge(4, 7);

        graph.addEdge(5, 4);
        graph.addEdge(5, 7);

        graph.addEdge(7, 6);

        System.out.println(graph);
    }
}
