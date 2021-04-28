package org.jasmineliuliuliu.learning.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are
 * stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and
 * return the sum?as a linked list.
 * <p>
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * <p>
 * 2 -> 4 -> 3<br> 5 -> 6 -> 4
 * <p>
 * result: 7 -> 0 -> 8
 * <p>
 * <b>Example 1:</b><br>
 * <p>
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * <p>
 * Output: [7,0,8]
 * <p>
 * Explanation: 342 + 465 = 807.
 * <p>
 * <b>Example 2:</b><br>
 * <p>
 * Input: l1 = [0], l2 = [0]
 * <p>
 * Output: [0]
 * <p>
 * <b>Example 3:</b><br>
 * <p>
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * <p>
 * Output: [8,9,9,9,0,0,0,1]
 * <p>
 * <b>Constraints</b>:
 * <li>The number of nodes in each linked list is in the range [1, 100].
 * <li>0 <= Node.val <= 9
 * <li>It is guaranteed that the list represents a number that does not have leading zeros.
 * <p>
 */
public class S0002AddTwoNumbers {

  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    List<ListNode> result = new ArrayList<>();
    int temp = 0;
    while ((l1.next != null || l2.next != null)) {
      int v = l1.val + l2.val + temp;
      if (v >= 10) {
        v = v % 10;
        temp = 1;
      } else {
        temp = 0;
      }
      result.add(new ListNode(v));
      if (l1.next != null) {
        l1 = l1.next;
      } else if (l2.next != null || temp > 0) {
        l1 = new ListNode(0);
      }
      if (l2.next != null) {
        l2 = l2.next;
      } else if (l1 != null || temp > 0) {
        l2 = new ListNode(0);
      }
    }
    int v = l1.val + l2.val + temp;
    if (v < 10) {
      result.add(new ListNode(v));
    } else {
      result.add(new ListNode(v % 10, new ListNode(v / 10)));
    }
    for (int i = 0; i < result.size() - 1; i++) {
      result.get(i).next = result.get(i + 1);
    }
    return result.get(0);
  }


  public static void main(String[] args) {
    S0002AddTwoNumbers solution = new S0002AddTwoNumbers();
    // Example 1: [7, 0, 8]
    ListNode l1 = solution
        .addTwoNumbers(genFromArray(new int[]{2, 4, 3}), genFromArray(new int[]{5, 6, 4}));
    System.out.println("Example 1: " + listNodesToString(l1));

    // Example 2: [0]
    ListNode l2 = solution
        .addTwoNumbers(genFromArray(new int[]{0}), genFromArray(new int[]{0}));
    System.out.println("Example 2: " + listNodesToString(l2));

    // Example 3: [8, 9, 9, 9, 0, 0, 0, 1]
    ListNode l3 = solution
        .addTwoNumbers(genFromArray(new int[]{9, 9, 9, 9, 9, 9, 9}),
            genFromArray(new int[]{9, 9, 9, 9}));
    System.out.println("Example 3: " + listNodesToString(l3));

    // Example 4: [8, 9, 3]
    ListNode l4 = solution
        .addTwoNumbers(genFromArray(new int[]{1, 8, 3}), genFromArray(new int[]{7, 1}));
    System.out.println("Example 4: " + listNodesToString(l4));
  }

  public static ListNode genFromArray(int[] a) {
    List<ListNode> array = new ArrayList<>();
    for (int i = 0; i < a.length; i++) {
      array.add(new ListNode(a[i]));
    }
    for (int i = 0; i < array.size() - 1; i++) {
      array.get(i).next = array.get((i + 1));
    }
    return array.get(0);
  }

  public static String listNodesToString(ListNode l) {
    StringBuilder sb = new StringBuilder("[" + l.val);
    while (l.next != null) {
      l = l.next;
      sb.append(", " + l.val);
    }
    sb.append("]");
    return sb.toString();
  }

  public static class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

}
