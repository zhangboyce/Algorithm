package algorithm_design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class AbstractAdjGraph<T> implements Graph<T> {
    protected Map<T, Vertex<T>> vertexes;

    AbstractAdjGraph() {
        vertexes = new HashMap<>();
    }

    @Override
    public void addVertex(T element) {
        if (this.containsVertex(element)) return;

        Vertex vertex = new Vertex<>(element);
        this.vertexes.put(element, vertex);
    }

    protected <T> Vertex getVertex(T element) {
        return this.vertexes.get(element);
    }

    @Override
    public <T> boolean containsVertex(T element) {
        return vertexes.containsKey(element);
    }

    @Override
    public <T> void joinVertex(T element1, T element2) {

    }

    @Override
    public int getVertexCount() {
        return 0;
    }

    @Override
    public int getEdgeCount() {
        return 0;
    }

    /**
     * 图的节点数据结构
     * @param <T>
     */
    class Vertex<T> {
        List<Vertex<T>> vertexes;
        T element;

        Vertex(T element) {
            this.element = element;
            vertexes = new ArrayList<>();
        }

        /**
         * 将该节点与一个节点连接起来
         * @param element
         */
        void joinVertex(T element) {
            if (!AbstractAdjGraph.this.containsVertex(element)) {
                // TODO
//                AbstractAdjGraph.this.addVertex(element);
            }
            this.vertexes.add(AbstractAdjGraph.this.getVertex(element));
        }

        int degree() {
            return this.vertexes.size();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AbstractAdjGraph.Vertex)) return false;

            AbstractAdjGraph.Vertex vertex = (AbstractAdjGraph.Vertex) o;

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

            for (int i=0; i<this.vertexes.size(); i++)
                builder.append(this.vertexes.get(i).element).append(",");

            if (builder.length() > 2)
                builder.delete(builder.length()-1, builder.length());
            builder.append("]");

            return "{" + element +
                    "," + builder.toString() +
                    '}';
        }
    }

}
