package structure.heap;

import common.exception.OutOfQueueException;
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
        this.sort(this.rear - 1, this.front);
    }

    @Override
    public void decrease(int i, T offset) {
        if (this.rear < this.front &&
                !((0<i && i <this.rear) || (this.front < i && i < this.data.length-1))) {
            throw new OutOfQueueException("the index out of queue, index: " + i);
        }

        if (this.rear > this.front && !(i>=this.front && i<this.rear)) {
            throw new OutOfQueueException("the index out of queue, index: " + i);
        }

        this.data[i] = this.data[i] - offset;
    }

    @Override
    public void increase(int i, T offset) {

    }

    @Override
    public void delete(int i) {

    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    private void sort(int start, int end) {
        int i = start;
        for (; i != end; i --) {
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
