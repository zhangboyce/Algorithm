package structure.union_find;

/**
 * Created by boyce on 2014/9/3.
 */
public class QuickFind implements DisjointSet {

    /**
     *  array index is the element data, collection=array[index] is the element's collection name
     *  example: array [1, 2, 4, 5], element data is 0,1,2,3.
     *  element 0 belong to collection 1, similarly, element 2 belong to collection 4
     */
    private int[] elements;

    /**
     * construct a QuickFind
     * @param n elements size
     */
    public QuickFind(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        this.elements = new int[n];

        //set collection of each element to itself
        for (int i=0; i<n; i++)
            this.elements[i] = i;
    }

    /**
     * P and q are in the same collection, return true
     * O(1)
     */
    public boolean find(int p, int q) {
        return this.find(p) == this.find(q);
    }

    //find element's collection id
    public int find(int e) {
        return this.elements[e];
    }

    /**
     * unite collection p included and collection q included to a same collection
     * O(n)
     */
    public void unite(int p, int q) {
        int p_collection = this.elements[p];
        int q_collection = this.elements[q];

        for (int i=0; i<this.elements.length; i++)
            if (this.elements[i] == p_collection)
                this.elements[i] = q_collection;

    }

}
