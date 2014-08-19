package structure.heap;

import common.exception.OutOfQueueException;
import structure.queue.IArrayQueue;

/**
 * Created by boyce on 2014/8/14.
 * priority queue implemented array
 */
public class IArrayPriorityQueue
        extends IArrayQueue<Integer> implements IPriorityQueue {

    /**
     * sorted the array when offering evey time
     * @param t
     */
    @Override
    public void offer(Integer t) {
        super.offer(t);

        //sort array to make the rear element is the minimum
        this.sort(this.front, this.rear - 1);
    }

    @Override
    public void decrease(int i, Integer offset) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the index out of queue, index: " + i);

        this.data[i] = this.data[i] - offset;
        this.sort(i, this.rear-1);
    }

    @Override
    public void increase(int i, Integer offset) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the index out of queue, index: " + i);

        this.data[i] = this.data[i] + offset;
        this.sort(this.front, i);
    }

    @Override
    public void delete(int i) {
        int size1 = (rear - i + data.length)%data.length + (i - front + data.length)%data.length;
        if (size1 != this.size())
            throw new OutOfQueueException("the index out of queue, index: " + i);

        for (; i != this.rear; i++) {
            this.data[i%this.data.length] = this.data[(i+1)%this.data.length];
        }
        this.rear = (this.rear + this.data.length - 1) % this.data.length;
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
    private void sort(int start, int end) {
        int i = end;
        for (; i != start; i --) {
            // if i<0 && i!=start, the rear < the front
            if (i < 0)
                i = this.data.length - 1;

            Integer temp = this.data[i];
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
        iPriorityQueue.offer(9);
        iPriorityQueue.offer(8);
        iPriorityQueue.offer(-1);
        iPriorityQueue.offer(5);
        iPriorityQueue.offer(2);

        System.out.println(iPriorityQueue.poll());
        System.out.println(iPriorityQueue);
    }
}
