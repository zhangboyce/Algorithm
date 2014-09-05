package algorithm.other;

import java.util.Arrays;

/**
 * Created by boyce on 2014/9/4.
 */
public class BucketProblem {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_BUCKET_CAPACITY = 10;
    private Bucket[] buckets;
    private int size;

    public BucketProblem(int capacity) {
        if (capacity <=0 )
            throw new IllegalArgumentException();

        this.buckets = new Bucket[capacity];
    }

    public BucketProblem() {
        this(DEFAULT_CAPACITY);
    }

    public void nextFit(int data) {
        if (data <=0 || data > DEFAULT_BUCKET_CAPACITY) return;

        Bucket bucket = null;
        if (size > 0)
            bucket = this.buckets[size-1];

        if (null == bucket || !bucket.canFit(data)) {
            if (this.size >= this.buckets.length)
                this.ensureCapacity(1 << this.buckets.length);

            bucket  = new Bucket("1", DEFAULT_BUCKET_CAPACITY);
            this.buckets[size ++] = bucket;
        }
        bucket.fit(data);
    }

    public void firstFit(int data) {
        if (data <=0 || data > DEFAULT_BUCKET_CAPACITY) return;

        Bucket bucket = null;
        if (size > 0)
            for (int i=0; i<this.size; i++)
                if (this.buckets[i].canFit(data)) {
                    bucket = this.buckets[i];
                    break;
                }
        
        if (null == bucket) {
            if (this.size >= this.buckets.length)
                this.ensureCapacity(1 << this.buckets.length);

            bucket  = new Bucket("1", DEFAULT_BUCKET_CAPACITY);
            this.buckets[size ++] = bucket;
        }
        bucket.fit(data);
    }

    private void ensureCapacity(int capacity) {
        Bucket[] newBuckets = new Bucket[capacity];
        for (int i=0; i<this.buckets.length; i++)
            newBuckets[i] = this.buckets[i];

        this.buckets = newBuckets;
    }

    private static class Bucket {
        //bucket name
        private String name;

        //bucket elements
        private int[] elements;
        private int size;

        //bucket capacity
        private int capacity;

        //bucket remaining capacity
        private int remainingCapacity;

        private final static int ELEMENTS_LENGTH = 10;

        private Bucket(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
            this.remainingCapacity = this.capacity;
            this.elements = new int[ELEMENTS_LENGTH];
        }

        private Bucket(String name) {
            this(name, 1);
        }

        private boolean canFit(int element) {
            if (element <= 0) return false;

            if (element > this.remainingCapacity)
                return false;
            return true;
        }

        private boolean fit(int element) {
            if (!canFit(element)) return false;

            if (this.size >= this.elements.length)
                this.ensureCapacity(1 << this.elements.length);

            this.elements[size++] = element;
            this.remainingCapacity -= element;

            return true;
        }

        private void ensureCapacity(int capacity) {
            int[] newTree = new int[capacity];
            for (int i=0; i<this.elements.length; i++)
                newTree[i] = this.elements[i];

            this.elements = newTree;
        }

        @Override
        public String toString() {
            return this.name + ": " + Arrays.toString(elements);
        }
    }
}
