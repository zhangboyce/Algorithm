package algorithm.other;

import algorithm.sort.InsertSorting;
import algorithm.sort.Sorting;

/**
 * Created by boyce on 2014/9/4.
 */
public class BucketList {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_BUCKET_CAPACITY = 10;
    private Bucket[] buckets;
    private int size;

    /**
     * construct a BucketList
     * @param capacity
     */
    public BucketList(int capacity) {
        if (capacity <=0 )
            throw new IllegalArgumentException();

        this.buckets = new Bucket[capacity];
    }

    public BucketList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * next fit, if next bucket has full, new a bucket to fit
     * @param data
     */
    public void nextFit(int data) {
        if (data <=0 || data > DEFAULT_BUCKET_CAPACITY) return;

        Bucket bucket = null;
        //get the next bucket
        if (size > 0)
            bucket = this.buckets[size-1];

        //size = 0 or the next bucket cannot fit the data
        if (null == bucket || !bucket.canFit(data)) {
            if (this.size >= this.buckets.length)
                this.ensureCapacity(1 << this.buckets.length);

            bucket  = new Bucket("b_" + this.size , DEFAULT_BUCKET_CAPACITY);
            this.buckets[size ++] = bucket;
        }
        bucket.fit(data);
    }

    /**
     * first fit, find a bucket from bucket-array 0 to n
     * util find the first bucket can fit the data
     * @param data
     */
    public void firstFit(int data) {
        if (data <=0 || data > DEFAULT_BUCKET_CAPACITY) return;

        Bucket bucket = null;
        if (size > 0)
            //find the first bucket that can fit data
            for (int i=0; i<this.size; i++)
                if (this.buckets[i].canFit(data)) {
                    bucket = this.buckets[i];
                    break;
                }

        // if size =0 or no bucket in array can fit the data
        if (null == bucket) {
            if (this.size >= this.buckets.length)
                this.ensureCapacity(1 << this.buckets.length);

            bucket  = new Bucket("b_" + this.size , DEFAULT_BUCKET_CAPACITY);
            this.buckets[size ++] = bucket;
        }
        bucket.fit(data);
    }

    /**
     * fit a array
     * sort the array desc
     * @param array
     */
    public void fit(Integer[] array) {
        if (null == array || array.length == 0)
            return;

        Sorting sorting = new InsertSorting();
        sorting.sortDescending(array);

        for (int i=0; i<array.length; i++)
            this.firstFit(array[i]);
    }

    private void ensureCapacity(int capacity) {
        Bucket[] newBuckets = new Bucket[capacity];
        for (int i=0; i<this.buckets.length; i++)
            newBuckets[i] = this.buckets[i];

        this.buckets = newBuckets;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i=0; i<this.size; i++)
            builder.append(this.buckets[i].toString()).append(", ");

        if (builder.length() > 3)
            builder.delete(builder.length()-2, builder.length());
        builder.append("]");

        return  "buckets: " + builder.toString();
    }

    /**
     * bucket class
     */
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
            StringBuilder builder = new StringBuilder("[");

            for (int i=0; i<this.size; i++)
                builder.append(this.elements[i]).append(", ");

            if (builder.length() > 3)
                builder.delete(builder.length()-2, builder.length());
            builder.append("]");

            return this.name + ": " + builder.toString();
        }
    }

    public static void main(String[] args) {
        BucketList bucket1 = new BucketList();
        bucket1.nextFit(1);
        bucket1.nextFit(2);
        bucket1.nextFit(7);
        bucket1.nextFit(4);
        bucket1.nextFit(1);
        bucket1.nextFit(1);
        bucket1.nextFit(2);
        bucket1.nextFit(3);
        bucket1.nextFit(5);
        bucket1.nextFit(1);
        bucket1.nextFit(4);
        bucket1.nextFit(3);
        bucket1.nextFit(6);
        bucket1.nextFit(7);
        bucket1.nextFit(5);
        bucket1.nextFit(3);

        System.out.println(bucket1);

        BucketList bucket2 = new BucketList();
        bucket2.firstFit(1);
        bucket2.firstFit(2);
        bucket2.firstFit(7);
        bucket2.firstFit(4);
        bucket2.firstFit(1);
        bucket2.firstFit(1);
        bucket2.firstFit(2);
        bucket2.firstFit(3);
        bucket2.firstFit(5);
        bucket2.firstFit(1);
        bucket2.firstFit(4);
        bucket2.firstFit(3);
        bucket2.firstFit(6);
        bucket2.firstFit(7);
        bucket2.firstFit(5);
        bucket2.firstFit(3);

        System.out.println(bucket2);
    }
}
