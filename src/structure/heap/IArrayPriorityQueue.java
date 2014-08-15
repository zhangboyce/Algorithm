package structure.heap;

import structure.queue.IArrayQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented array
 */
public class IArrayPriorityQueue<T extends Comparable>
        extends IArrayQueue<T> implements IPriorityQueue<T> {

    /**
     * sorted the array when offering evey time
     * @param t
     */
    @Override
    public void offer(T t) {
        super.offer(t);

        //sort array to make the rear element is the minimum
        int i = this.rear - 1;
        for (; i != this.front; i --) {
            if (i < 0)
                i = this.data.length - 1;

            T temp = (T)this.data[i];
            int j;
            for (j = i-1; j>=0; j--) {
                if(temp.compareTo(this.data[j]) < 0){
                    this.data[j+1] = this.data[j];
                }else{
                    break;
                }
            }
            this.data[j+1] = temp;
        }
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    public static void main(String[] args) {
        IPriorityQueue iPriorityQueue = new IArrayPriorityQueue();
        iPriorityQueue.offer(3);
        iPriorityQueue.offer(8);
        iPriorityQueue.offer(-1);
        iPriorityQueue.offer(5);
        iPriorityQueue.offer(2);

        System.out.println(iPriorityQueue.poll());
        System.out.println(iPriorityQueue);
    }
}
