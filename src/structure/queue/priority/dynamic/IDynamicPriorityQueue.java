package structure.queue.priority.dynamic;

import common.utils.ObjectUtils;
import structure.queue.priority.IPriorityQueue;

/**
 * Created by boyce on 2014/8/19.
 */
public interface IDynamicPriorityQueue extends IPriorityQueue<IDynamicPriorityQueue.DynamicPriorityQueueElement> {


    /**
     * decrease offset value indexOf i
     * @param i
     */
    public void decrease(int i, Integer offset);

    /**
     * increase offset value indexOf i
     * @param i
     */
    public void increase(int i, Integer offset);

    public void remove(int i);

    public <T> void offer(T element, int priority);

    public static class DynamicPriorityQueueElement<T> implements Comparable<DynamicPriorityQueueElement> {

        protected T element;
        protected int priority;

        public DynamicPriorityQueueElement(T element, int priority) {
            this.element = element;
            this.priority = priority;
        }

        public DynamicPriorityQueueElement(T element) {
            this.element = element;
        }

        @Override
        public int compareTo(DynamicPriorityQueueElement o) {
            return this.priority - o.priority;
        }

        @Override
        public String toString() {
            return element.toString();
        }

        public DynamicPriorityQueueElement decrease(int offset) {
            this.priority = this.priority - offset;

            return this;
        }

        public DynamicPriorityQueueElement increase(int offset) {
            this.priority = this.priority + offset;

            return this;
        }
    }

}
