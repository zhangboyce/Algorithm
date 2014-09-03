package structure.union_find;

/**
 * Created by boyce on 2014/9/3.
 */
public interface DisjointSet {

    /**
     * if p and q are in the same collection, return true, else return false
     * @param p element p
     * @param q element q
     * @return
     */
    public boolean find(int p, int q);

    /**
     * find element's collection id
     * @param e
     * @return
     */
    public int find(int e);

    /**
     * unite collection p included and collection q included to a same collection
     * @param p
     * @param q
     */
    public void unite(int p, int q);
}
