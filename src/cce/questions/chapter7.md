## 第七章：摊还分析方法与不相交集合数据结构
#### 一、填空题（共20空。共50分）

1. 对一个栈的数据结构执行$n$次$push$操作的总代价为\_\_\_\_\_\_\_\_，每次操作的平均代价为\_\_\_\_\_\_\_\_.假设有操作$pop(k)$表示连续从栈中$pop$出$k$个元素。那么对栈执行$n$次操作（操作可以是$pop$，$push$和$pop(k)$）的总代价为\_\_\_\_\_\_\_\_.



2. 向一个数组中不断的插入元素，当数组被插满时将重新构造一个原来数组$2$倍长度的新数组并将所有元素拷贝到新数组中，继续在新数组中插入元素。设每次插入一个元素的代价为$1$，将元素从旧数组移动到新数组的代价也为$1$。如果我们执行$n$次插入操作，一次操作的最坏情况时间为\_\_\_\_\_\_\_\_，$n$次操作的总时间为\_\_\_\_\_\_\_\_，每次操作的平均时间为\_\_\_\_\_\_\_\_.



3. 一个不相交集数据结构维护了一个互不相交的动态集合$\xi =\left\{ { S }_{ 1 },{ \quad S }_{ 2 },\quad ...\quad ,{ \quad S }_{ k } \right\} $，每一个集合${S}_{i}$我们用一个代表元素来标识，它是这个集合的某一个成员。定义不相交集的操作$find(x)$返回元素$x$所在集合的标识。对于不想交集中的任意两个元素$x,y$，$x,y$属于同一个集合的充分必要条件是\_\_\_\_\_\_\_\_.



4. 定义在不相交集上的操作$union(x,y)$为将不相交集上的包含$x$和$y$的两个动态集合${S}_{x}$，${S}_{y}$合并成一个新的集合${ S }_{ x }\cup { S }_{ y }$，由于要求各个集合不相交，所以要将原有的集合${S}_{x}$，${S}_{y}$从不相交集中删除。定义$insert(x)$操作为向不相交集中插入一个新的仅包含$x$元素的集合。所以对一个空的不相交集执行$n$次的$insert$操作后，可以对不相交集执行的$union$操作最多为\_\_\_\_\_\_\_\_次。



5. 假如我们用一个链表表示不相交集中的集合，每个集合都拥有链表的$head$和$tail$指针，$head$为该集合的标识，集合中每个元素都有一个指向$head$的指针，所以$find(x)$操作的时间复杂度为\_\_\_\_\_\_\_\_；因为$insert(x)$操作只需创建一个只有$x$元素的新链表，所以$insert(x)$操作的时间复杂度为\_\_\_\_\_\_\_\_；$union(x,y)$操作需要将包含$x$和$y$的两个链表合并且需要更新合并后集合中元素的标识即指向新集合$head$的指针，所以$union(x,y)$操作的最坏时间为\_\_\_\_\_\_\_\_，最好时间为\_\_\_\_\_\_\_\_。假如$union(x,y)$操作我们总是将长度较小的链表拼接到较长的链表中并更新较小链表中元素的标识，那么一个具有$m$个$insert$、$union$和$find$操作的序列(其中有$n$个$insert$操作)的总时间为\_\_\_\_\_\_\_\_.



6. 如果我们用一棵树来表示不相交集中的每个集合，树的根节点为集合的标识，树中每个节点仅有一个指向父节点的引用，根节点的父节点为自己。$insert$操作为创建一棵只有一个节点的树，$find$操作沿着指向父节点的指针找到树根，$union$操作将一个树的根指向另一个树的根。那么对于一个用森林表示的空的不相交集首先执行$n$次$insert$操作，然后执行$n-1$次$union$操作。那么再在这个不相交集中执行一个$find$操作的最好时间为\_\_\_\_\_\_\_\_，最坏时间为\_\_\_\_\_\_\_\_。



7. 对于一个用森林表示的不相交集，如果每一次的$union$操作都让元素较少的树的根指向元素较多的树的根，那么这样的不相交集的$find$操作时间复杂度为\_\_\_\_\_\_\_\_；如果每一次的$union$操作都让高度较小的树的根指向高度较大的树的根，那么将两棵高度不等的树$union$后，所有节点的高度将\_\_\_\_\_\_\_\_（增加、较小或不变）；将两棵高度相等的树$union$后，新的树高度将\_\_\_\_\_\_\_\_（增加、较小或不变）。



8. 对于一个用森林表示的不相交集，如果每一次的$find(x)$操作都顺便将从根到$x$路径上的所有节点的父节点直接指向树的根，这样的一次操作称为路劲压缩。以下是带有路径压缩的$find$伪代码，请补充：

   ```javascript
   find(x) {
       // x.p 指x的父节点
       if (x != x.p)
           ________________;
       return  ________________;
   }
   ```

#### 二、解答题（共2题，每题25分。共50分）

1. 有序数组上的二分查找花费对数时间，但是插入一个新元素的时间与数组的长度呈线性关系。我们可以通过维护多个有序数组来提高插入性能。具体地，假定我们希望支持$n$元集合的插入与查找操作。令$k=\left\lceil \log _{ 2 }{ \left( n+1 \right)  }  \right\rceil $，令$n$的二进制表示为$\left< { n }_{ k-1 },\quad { n }_{ k-2 }\quad ,...,{ \quad n }_{ 0 } \right> $，我们维护$k$个有序数组${ A }_{ 0 },{ A }_{ 1 }, … ,{ A }_{ k-1 }$，对于$i=0,1, …, k-1$，数组${A}_{i}$的长度为${ 2 }^{ i }$，当${n}_{i}=1$时，数组${A}_{i}$为满数组；当${n}_{i}=0$时，数组${A}_{i}$为空数组。因此$k$个数组中保存的元素总数为$\sum _{ i=0 }^{ k-1 }{ { n }_{ i } } { 2 }^{ i }=n$。虽然单独的每个数组都是有序的，但是不同数组中的元素不存在特定的大小关系。

   a. 设计查找算法，分析其最坏情况运行时间。

   b. 设计插入算法，分析其最坏情况运行时间和摊还时间。

   c. 试研究如何实现删除。
























2. 阅读以下对不相交集操作的伪代码，回答以下问题：

   ```javascript
   1. for i=1 to 16 insert(x_i);
   2. for i=1 to 13 by 2 union(x_i, x_i+1);
   3. for i=1 to 9 by 4 union(x_i, x_i+2);
   4. union(x_15, x_1);
   5. union(x_7, x_10);
   6. union(x_16, x_13);
   7. find(x_2);
   8. find(x_9);
   ```

   a. 假如采用链表的数据结构实现，$union(x,y)$操作将$y$中的元素添加到$x$中并更新$y$中元素指向标识的指针。那么第$3$行代码执行完后，不相交集中有几个链表？分别是？	

   b. 假如采用链表的数据结构实现，$union(x,y)$操作将$x$和$y$中元素较少的链表添加到元素较多的链表中并更新较少集合中的元素的标识指针。那么第$6$行代码执行完后，不相交集中有几个链表？分别是？	

   c. 假如采用树的数据结构实现，$union(x,y)$操作是将$x$和$y$中高度较小的树合并到高度较大的树中。那么那么第$5$行代码执行完后，不相交集中有几棵树？分别是？

   d. 假如采取c中的实现方案和合并策略执行完第$6$行代码后，分别给出采用路径压缩方式查找的策略下，第$7$，$8$行代码分别执行完后的森林的情况？








