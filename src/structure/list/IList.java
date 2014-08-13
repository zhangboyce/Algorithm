package structure.list;

/**
 * Created by boyce on 2014/7/6.
 */
public interface IList<T> extends IIterable {

    /**
     * Get the list elements number
     * @return element number
     */
    public int size();

    /**
     * Insert a element into list at index i
     * @param i the element insert position
     * @param t the inserted element
     */
    public void insert(int i, T t);

    /**
     * Add a element in list last index
     * @param t added element
     */
    public void add(T t);

    /**
     * Remove a element at index i
     * @param i the removed element index
     * @return the removed element object
     */
    public T remove(int i);

    /**
     * Remove object t from the list
     * @param t the removed element object
     * @return the removed element object
     */
    public T remove(T t);

    /**
     * Judge whether the list is empty
     * @return true/the list is empty, false/the list is not empty
     */
    public boolean isEmpty();

    /**
     * Get a element at index i from list
     * @param i the got element index
     * @return the got element
     */
    public T get(int i);

    /**
     * Return the element index in list if it equals t
     * @param t t
     * @return the index of t in the list
     */
    public int indexOf(T t);

    /**
     * remove all elements of list
     */
    public void clear();

    public boolean contains(T t);
}
