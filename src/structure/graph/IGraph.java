package structure.graph;

import structure.list.IArrayList;
import structure.list.IList;

/**
 * Created by boyce on 2014/8/20.
 */
public class IGraph<T> {

   private IList<Vertex<T>> vertexs;
   private IList<Edge> edges;

    public IGraph() {
        vertexs = new IGraphArrayList<Vertex<T>>();
        edges = new IGraphArrayList<Edge>();
    }

    /**
     * add a single vertex to graph
     * @param element
     */
    public void addVertex(T element) {
        if (null == element) return;

        Vertex<T> vertex = new Vertex<T>(element);
        this.vertexs.add(vertex);
    }

    /**
     * add a edge to graph
     * @param element1
     * @param element2
     * @param weight
     */
    public void addEdge(T element1, T element2, int weight) {
        if (null == element1 || null == element2) return;

        //create two vertex
        Vertex<T> vertex1 = new Vertex<T>(element1);
        Vertex<T> vertex2 = new Vertex<T>(element2);

        //create a edge
        Edge edge = new Edge(vertex1, vertex2, weight);
        this.addEdge(vertex1, vertex2, edge);
    }

    /**
     * add a edge to graph
     * @param element1
     * @param element2
     */
    public void addEdge(T element1, T element2) {
        if (null == element1 || null == element2) return;

        //create two vertex
        Vertex<T> vertex1 = new Vertex<T>(element1);
        Vertex<T> vertex2 = new Vertex<T>(element2);

        //create a edge
        Edge edge = new Edge(vertex1, vertex2);
        this.addEdge(vertex1, vertex2, edge);
    }

    private void addEdge(Vertex<T> vertex1, Vertex<T> vertex2, Edge edge) {
        this.edges.add(edge);

        this.vertexs.add(vertex1);
        this.vertexs.add(vertex2);

        this.vertexs.get(this.vertexs.indexOf(vertex1)).addAdjVertex(vertex2);
        this.vertexs.get(this.vertexs.indexOf(vertex2)).addAdjVertex(vertex1);
    }

    @Override
    public String toString() {
        return "IGraph{" +
                "vertexs=" + vertexs +
                '}';
    }

    /**
     * graph edge
     */
    private static class Edge {
        private Vertex vertex1;
        private Vertex vertex2;
        private int weight;

        private Edge(Vertex vertex1, Vertex vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        private Edge(Vertex vertex1, Vertex vertex2) {
            this(vertex1, vertex2, 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;

            Edge edge = (Edge) o;

            if (vertex1 != null ? !vertex1.equals(edge.vertex1) : edge.vertex1 != null) return false;
            if (vertex2 != null ? !vertex2.equals(edge.vertex2) : edge.vertex2 != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = vertex1 != null ? vertex1.hashCode() : 0;
            result = 31 * result + (vertex2 != null ? vertex2.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "vertex1=" + vertex1 +
                    ", vertex2=" + vertex2 +
                    ", weight=" + weight +
                    '}';
        }
    }

    /**
     * Graph vertex
     * @param <T>
     */
    private static class Vertex<T> {
        //vertex data
        private T element;
        //this vertex's adjacent vertexs list
        private IList<Vertex<T>> adjVertexs;

        private Vertex(T element) {
            this.element = element;
            this.adjVertexs = new IGraphArrayList<Vertex<T>>();
        }

        private void addAdjVertex(Vertex vertex) {
            this.adjVertexs.add(vertex);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;

            Vertex vertex = (Vertex) o;

            if (!element.equals(vertex.element)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return element.hashCode();
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "element=" + element +
                    ", adjVertexs=" + adjVertexs +
                    '}';
        }
    }

    private static class IGraphArrayList<T> extends IArrayList<T> {
        @Override
        public void add(T t) {
            if (this.contains(t))
                return;

            super.add(t);
        }

    }

    public static void main(String[] args) {
        IGraph<Integer> graph = new IGraph<Integer>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        System.out.println(graph);
    }
}
