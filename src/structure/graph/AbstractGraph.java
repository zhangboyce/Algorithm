package structure.graph;

import structure.list.IArrayList;
import structure.list.IList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by boyce on 2014/8/20.
 */
public abstract class AbstractGraph<T> implements IGraph<T> {

    protected Map<T, Vertex<T>> vertexs;

    public AbstractGraph() {
        vertexs = new HashMap<>();
    }

    @Override
    public void addVertex(T element) {
        if (null == element) return;

        Vertex<T> vertex = new Vertex<T>(element);
        if (this.vertexs.containsKey(element)) return;

        this.vertexs.put(element, vertex);
    }

    @Override
    public String toString() {
        return vertexs.toString();
    }

    /**
     * Graph vertex
     * @param <T>
     */
    protected class Vertex<T> {
        //vertex data
        protected T element;
        //this vertex's adjacent vertexs list
        protected IList<Vertex<T>> adjVertexs;

        //vertex's in-degree
        protected int inDegree;

        protected Vertex(T element) {
            this.element = element;
            this.adjVertexs = new IGraphArrayList<Vertex<T>>();
        }

        public void addAdjVertex(T element) {
            if (null == element)
                return;

            Vertex<T> vertex;
            if (AbstractGraph.this.vertexs.containsKey(element)) {
                vertex = (Vertex<T>) AbstractGraph.this.vertexs.get(element);
            }
            else {
                throw new IllegalArgumentException();
            }

            vertex.inDegree += 1;
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
            StringBuilder builder = new StringBuilder("[");

            for (int i=0; i<this.adjVertexs.size(); i++)
                builder.append(this.adjVertexs.get(i).element).append(",");

            if (builder.length() > 2)
                builder.delete(builder.length()-1, builder.length());
            builder.append("]");

            return "{" + element +
                    "," + builder.toString() +
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


}
