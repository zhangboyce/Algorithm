package structure.queue.priority;

import structure.queue.IArrayQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented array
 */
public class IArrayPriorityQueue<T extends Comparable>
        extends IArrayQueue<T> implements IPriorityQueue<T> {

    public IArrayPriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    public IArrayPriorityQueue(int capacity) {
        this.data = (T[])new Comparable[capacity];
    }

    /**
     * sorted the array when offering evey time
     * @param t
     */
    @Override
    public void offer(T t) {
        super.offer(t);

        //sort array to make the rear element is the minimum
        this.sort(this.front, this.rear);
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    /**
     * sort the array index from start to end
     * @param start
     * @param end
     */
    protected void sort(int start, int end) {
        for (int i = start + 1; i != end; i ++) {
            i = (i + this.data.length) % data.length;
            T temp = this.data[i];
            int j;
            for (j = i-1; j != start-1; j --) {
                j = (j + this.data.length) % data.length;
                if(temp.compareTo(this.data[j]) < 0){
                    this.data[j+1] = this.data[j];
                }else{
                    break;
                }
            }
            this.data[j+1] = temp;
        }
    }

    public static void main(String[] args) {
        IPriorityQueue<Integer> iPriorityQueue = new IArrayPriorityQueue<Integer>();
        iPriorityQueue.offer(9);
        iPriorityQueue.offer(8);
        iPriorityQueue.offer(-1);
        iPriorityQueue.offer(5);
        iPriorityQueue.offer(2);

        System.out.println(iPriorityQueue.poll());
        System.out.println(iPriorityQueue.poll());
        System.out.println(iPriorityQueue.poll());
        System.out.println(iPriorityQueue);

        iPriorityQueue.offer(3);
        iPriorityQueue.offer(10);
        System.out.println(iPriorityQueue);
    }

}
