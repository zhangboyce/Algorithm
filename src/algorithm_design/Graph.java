package algorithm_design;

/**
 * 图的接口
 * @param <T>
 */
public interface Graph<T> {
    /**
     * 向图中添加一个节点
     * @param element 添加的节点
     */
    void addVertex(T element);

    /**
     * 向图中添加一条边
     * @param element1 该边连接的节点1
     * @param element2 该边连接的节点2
     */
    <T> void joinVertex(T element1, T element2);

    <T> boolean containsVertex(T element);

    /**
     * 返回图中的节点总数
     * @return 节点总数
     */
    int getVertexCount();

    /**
     * 返回图中的边总数
     * @return 边总数
     */
    int getEdgeCount();
}
