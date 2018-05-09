## 第一章：数学基础、算法分析与表、栈和队列数据结构
#### 一、填空题（共20题，每题2分。共40分）
1. ${ X }^{ A }\times{ X }^{ B }=$ ____.

2. ${ 2 }^{ N } + { 2 }^{ N }=$ ____.

3. $({ X }^{ A })^{ B}=$ ____.

4. $\frac { \log _{ x }{ B } }{ \log _{ x }{ A } } =$____.

5. $\log { A } + \log { B }=$____.

6. $\log { （{ A }^{ B }） }=$____.

7. $\sum _{ i=0 }^{ N }{ { 2 }^{ i } }=$____.

8. $\sum _{ i=1 }^{ \infty  }{ \frac { i }{ { 2 }^{ i } } }=$____.

9. 试求以下代码运行时间复杂度的大$O$表示法____.
    ```javascript
    for(i=0; i<n; i++) 
        for(j=0; j<i; j++) 
            k++;
    ```

10.  试求以下代码运行时间复杂度的大$O$表示法____.

    ```javascript
    for(i=0; i<n; i++) a[i] = 0;
    for(i=0; i<n; i++)
        for(j=0; j<n; j++)
            a[i] += a[j] + i + j;
    ```

11.  试求以下函数运行时间复杂度的大$O$表示法(target为一个长度为n的数组)____.

     ```java
     public int method sum(int[] a, int left, int right) {
         if (left == right) return a[left];
         
         int center = (left + right) / 2;
         int leftSum = sum(a, left, center);
         int rightSUm = sum(a, center + 1, right);
         
         return leftSum + rightSUm;
     }
     sum(target, 0, target.length - 1)
     ```

12.  试求以下函数运行时间复杂度的大$O$表示法(target为一个长度为n的数组)____.

     ```javascript
     function find(array, x) {
         let low = 0, high = array.length - 1;
         while(low <= high) {
             let mid = (low + high) / 2;
             if (array[mid] < x) low = mid + 1;
             else if (array[mid] > x) high = mid - 1;
             else return mid;
         }
         return -1;
     }
     find(target, 6);
     ```

13.  一个算法对于大小为100的输入花费0.5ms。如果该算法的运行时间复杂度为$O(n^{2})$，则解决输入大小为500的问题需要花费多少____ms（设低阶项可以忽略）.

14.  一个算法对于大小为100的输入花费0.5ms。如果该算法的运行时间复杂度为$O(N\log{N})$，则解决输入大小为500的问题需要花费多少____ms（设低阶项可以忽略，$\log{5}\approx 2.32$）.

15.  最坏的情况下，在一个长度为n的数组中插入或者删除一个元素的算法时间复杂度为____.

16.  最好的情况下，在一个长度为n的数组中插入或者删除一个元素的算法时间复杂度为____.

17.  平均的情况下，在一个长度为n的数组中插入或者删除一个元素的算法时间复杂度为____.

18.  最坏的情况下，在一个长度为n的链表中查询一个元素的算法时间复杂度为____.

19.  中缀表达式$a+b*c+(d*e+f)*g$的后缀表达式为____.

20.  像栈一样，队列的每一种操作，无论是链表实现还是数组实现，它们的时间复杂度都能达到____.

#### 二、代码题（共3题。共40分）
1. (每空2分。共10分)如下，我们定义一个链表的节点类Node:

    ```java
    class Node {
        Object data;
        Node next;
    }
    ```

    ​	再定义一个链表类LinkedList，请补充其中的实现:

    ```java
    class LinkedList {
        Node head;
        int size;

        /**
         * Get indexOf i-1:
         * i=0 return head, i=1, return head.next(indexOf=0)
         */
        private Node index(int i) {
            if (i < 0 || i > this.size)
                throw new IllegalArgumentException("Illegal indexOf, " + i);

            Node p = head;
            int j = 0;
            while (p != null && j < i) {
                p = p.next;
                j ++;
            }
            return p;
        }

        public void insert(int i, Node node) {
            Node p = this.index(i);
            ________________________;
            ________________________;
            size ++;
        }

        public Node remove(int i) {
            Node p = this.index(i);
            Node n = p.next;
            ________________________;
            ________________________;
            return n;
        }

        boolean isEmpty() {
            ________________________;
        }
    }
    ```

2. (每空2分。共10分)如下，我们用数组实现一个栈，请补充其中的实现:
    ```java
        class ArrayStack {
            Object[] stack;
            int size;

            private void ensureCapacity() {
                Object[] newStack = new Object[this.stack.length * 2];
                for (int i=0; i<this.stack.length; i++)
                    newStack[i] = this.stack[i];

                this.stack = newStack;
            }

            public void push(Object t) {
                if (this.size == this.stack.length)
                    this.ensureCapacity();

                ________________________;
            }

             public Object peek() {
                if (this.size == 0)
                    throw new EmptyStackException();

                ________________________;
            }

            public Object pop() {
                T t = this.peek();

                ________________________;

                return t;
            }

            public void clear() {
                ________________________;
                ________________________;
            }
        }
    ```

3. (每空4分。共20分)如下，我们用循环数组实现一个队列，请补充其中的实现:
    ```java
        class ArrayQueue {
            Object[] data; // 队列元素
            int front; // 队首元素下标
            int rear; // 队尾元素下标

            private void ensureCapacity() {
                Object[] newQueue = new Object[this.data.length * 2];

                for (int i=front; i != rear; i = (i+1) % data.length)
                    newQueue[i] = this.data[i];

                if (this.rear < this.front) {
                    this.rear = this.front + this.size();
                }
                this.data = newQueue;
            }

            public void offer(Object t) {
                if ((rear + 1) % data.length == front) {
                    this.ensureCapacity();
                }

                ________________________;
                ________________________;
            }

             public Object peek() {
                if (this.size() == 0)
                    throw new EmptyQueueException();

                ________________________;
            }

            public Object poll() {
                T t = this.peek();

                ________________________;

                return t;
            }

            public int size() {
                ________________________;
            }
        }
    ```

#### 三、解答题（共2题。共20分）
1. 证明公式$\sum _{ i=1 }^{ N }{ (2i - 1) }={N}^{2}$成立。(提示, 可采用数学归纳法证明)














2. 请简述(可用伪代码表示)一下用数组实现队列的思路, 并说明使用循环数组实现的优点。







