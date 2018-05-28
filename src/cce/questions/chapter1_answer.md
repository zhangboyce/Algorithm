## 第一章：数学基础、算法分析与表、栈和队列数据结构(答案)
#### 一、填空题（共20题，每题2分。共40分）
1. ${ X }^{ A + B } $.   2. ${ 2 }^{ N + 1 } $.   3. ${ X }^{ AB }$.   4. $\log _{ A }{ B }$.   5. $\log { AB }$.   6. $B\log { A }$.   7. ${ 2 }^{ N + 1 } - 1 $.   8. $2$.   9. $O({ n }^{ 2 })$   10. $O({ n }^{ 2 })$   11. $O(n\log { n })$   12. $O(\log { n })$   13. 12.5ms.   14. 5.575ms.   15. $O(n)$.   16. $O(1)$.   17. $O(n)$.   18. $O(n)$.   19. $ abc*+de*f+g*+ $   20. $O(1)$.

#### 二、代码题（共3题。共40分）
1.(每空2分。共10分)
```java
    node.next = p.next;
    p.next = node;

    p.next = n.next;
    size --;

    return size == 0;
```
2. (每空2分。共10分)
```java
    this.stack[this.size ++] = t;

    return this.stack[this.size - 1];

    this.stack[(this.size --) -1] = null;

    this.size = 0;
    this.stack = new Object[default_length];
```
3. (每空4分。共20分)
```java
    data[rear] = t;
    rear = (rear + 1) % data.length;

    return data[front];

    front = (front + 1) % data.length;

    return (rear - front + data.length) % data.length;
```

#### 三、解答题（共2题。共20分）答案: 略。