## 第六章：排序与顺序统计量
#### 一、填空题（共10题，每题5分。共50分）

1. 数组$[34,8,64,51,32,21]$中有\_\_\_\_个逆序，插入排序交换完第$4$个逆序后数组变为\_\_\_\_。
2. 对于由$N$个互异的数组成的数组中，平均的逆序数有\_\_\_\_个；所以通过交换相邻元素进行排序的任何算法平均都需要\_\_\_\_的时间复杂度，因为一次交换相邻元素只能消除一个逆序。
3. 在一个数组中交换元素$a[i]$与$a[i+k]$，假如它们是逆序的，那么交换后在数组中去掉的逆序对最少为\_\_\_\_个，最多为\_\_\_\_个.
4. 希尔排序的思想依据是通过一次交换相距较远的逆序，平均情况不止去掉一个逆序的事实。所以希尔排序的平均情况下的时间复杂度要好于插入排序，但是希尔排序的时间复杂度与选取的增量序列有密切关系。写出用增量$3$对数组$[3,5,7,2,8,1,9,4,6,0]$排序的结果\_\_\_\_.
5. 归并排序的大致过程是，递归地将数组的一半元素进行排序然后将两个排序的序列通过依次比较合并得到最终的排序结果。将长度均为$N$的两个已排序数组依次比较得到一个长度为$2N$的已排序数组，需要的比较次数为\_\_\_\_.归并排序的时间复杂度为\_\_\_\_.
6. 如果枢纽元总是选择数组的第一个元素，那么对于一个已排序的数组，使用快速排序的运行时间为\_\_\_\_，那么对于反排序的数组运行时间为\_\_\_\_。
7. 对于有$n$个互异元素的数组而言，数组中的元素所有可能的排列顺序有\_\_\_\_种，所以如果只使用元素间比较的任何排序算法在最坏情况下至少都需要\_\_\_\_次比较。
8. 在一个有$n$个元素的数组中，我们需要进行\_\_\_\_次比较才能找出最小值，同样，需要进行\_\_\_\_次比较才能找出最大值。如果同时找出最大值和最小值呢，至少需要_\_\_\_次比较。
9. 找到$n$个元素中第二小的元素需要至少_\_\_\_次比较。
10. 堆排序的时间复杂度为_\_\_\_.

#### 二、解答题（共5题，每题10分。共50分）

1. a. 写出使用插入排序对数组$[3,1,4,5,9,2,6,5]$排序的每一个中间过程数组；b. 写出使用增量$3,6$对数组$[9,8,7,6,5,4,3,2,1]$的排序结果。








2. 写出使用快速排序对数组$[5,2,3,6,7,9,1,8,4]$排序的过程。










3. 设计一个算法，它能够对任何给定的介于$0$到$k$之间的$n$个整数进行预处理，使之能够在$O(1)$的时间内返回在区间$[a, b]$中的元素个数。算法的时间应为$O（n+k）$










4. 对一个长度为$n$，且数组中所有的值不是$0$就是$1$的数组排序。对这样一个数组排序的算法可能具有如下特性。

   i. 算法的时间代价是$O（n）$；

   ii. 算法是稳定的；

   iii. 算法是原址排序的，除了输入数组以外只需要固定的额外空间。

   a. 给出一个满足条件i和条件ii的算法。

   b. 给出一个满足条件i和条件iii的算法。

   c. 给出一个满足条件ii和条件iii的算法。













5. 给定一个包含$n$个元素的数组，我们希望利用基于比较的算法找出按顺序排列的前$i$个最大元素。请分别设计能实现下列每一项要求，并具有最好的时间复杂度的算法。并给出算法以$n$和$i$表示的运行时间。（可使用伪代码）

   a. 对数组排序，并找出前$i$个最大数。

   b. 对数组建立一个最大优先队列，并对队列调用$i$次获取根节点的方法。

   c. 利用顺序统计量算法来获取到第$i$大的元素，然后将它作为主元划分数组，再对前$i$大的数排序。