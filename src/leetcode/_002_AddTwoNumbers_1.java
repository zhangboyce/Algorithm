package leetcode;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example:
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 */
public class _002_AddTwoNumbers_1 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode n1 = l1;
        ListNode n2 = l2;

        ListNode root = new ListNode(null);
        ListNode p = root;
        int t = 0;

        while (t != 0 || n1 != null || n2 != null) {
            int v1 = n1 != null ? n1.val : 0;
            int v2 = n2 != null ? n2.val : 0;

            int sum = v1 + v2 + t;
            int v = sum % 10;
            t = sum / 10;

            p = p.next = new ListNode(v);

            n1 = n1 == null ? null: n1.next;
            n2 = n2 == null ? null : n2.next;
        }

        return root.next;
    }

    public static void main(String[] args) {
        _002_AddTwoNumbers_1 addTwoNumbers_002_1 = new _002_AddTwoNumbers_1();

        ListNode node1 = _002_AddTwoNumbers_1.gen(new int[]{4});
        ListNode node2 = _002_AddTwoNumbers_1.gen(new int[]{9});

        ListNode node = addTwoNumbers_002_1.addTwoNumbers(node1, node2);

        System.out.println(node1.toString());
        System.out.println(node2.toString());
        System.out.println(node.toString());
    }

    private static ListNode gen(int[] nums) {
        if (nums == null) return null;
        if (nums.length == 0) return new ListNode(null);

        ListNode root = new ListNode(null);
        ListNode p = root;
        for (int n: nums) {
            p = p.next = new ListNode(n);
        }
        return root.next;
    }

    static class ListNode {
        Integer val;
        ListNode next;
        ListNode(Integer x) {
            this.val = x;
        }

        @Override
        public String toString() {
            if (val == null) return "null";
            String str = val + "";
            ListNode n = next;
            while (n != null) {
                str += (" > " + n.val);
                n = n.next;
            }
            return str;
        }
    }
}
