package structure.union_find;

/**
 * Created by boyce on 2014/9/3.
 */
public class QuickUnite implements DisjointSet {

    /**
     *  array index is the element data, parent=array[index] is the element's parent element,
     *  if a element's parent is -1, the element is the root, all it's children include itself in a same collection
     */
    private int[] elements;

    /**
     * construct a QuickUnite
     * @param n elements size
     */
    public QuickUnite(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        this.elements = new int[n];

        //set parent of each element to -1
        for (int i=0; i<n; i++)
            this.elements[i] = -1;
    }

    /**
     * p and q are in the same collection, return true
     * O(1)
     */
    public boolean find(int p, int q) {
        return this.find(p) == this.find(q);
    }

    /**
     * find element's collection id (root element)
     * O(depth)
     */
    public int find(int e) {
        //if e's parent is not -1, e is not the root
        while (this.elements[e] != -1)
            //find e's parent continue util find root
            e = this.elements[e];

        return e;
    }

    /**
     * unite collection p included and collection q included to the same collection
     * O(n)
     */
    public void unite(int p, int q) {
        int p_root = find(p);
        int q_root = find(q);

        //set p_root's parent to q_root, p and q has the same root, the same collection
        this.elements[p_root] = q_root;
    }

}
