package structure.graph;

import structure.list.IList;

/**
 * Created by boyce on 2014/8/22.
 * graph interface
 */
public interface IGraph<T> {

    /**
     * add a single vertex to graph
     * @param element
     */
    public void addVertex(T element);

    /**
     * add a edge to graph
     * @param element1
     * @param element2
     * @param weight
     */
    //public void addEdge(T element1, T element2, int weight);

    /**
     * add a edge to graph
     * @param element1
     * @param element2
     */
    public void addEdge(T element1, T element2);

}
