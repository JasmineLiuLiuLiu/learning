package org.jasmineliuliuliu.learning.basic;

import java.util.ArrayList;
import java.util.List;

class Solution {

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

  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    List<ListNode> result = new ArrayList<>();
    int temp = 0;
    while ((l1.next != null && l2.next != null) || temp > 0) {
      int v = l1.val + l2.val + temp;
      if (v >= 10) {
        v = v % 10;
        temp = 1;
      } else {
        temp = 0;
      }
      result.add(new ListNode(v, null));
      if (l1.next != null) {
        l1 = l1.next;
      } else if (temp > 0) {
        l1 = new ListNode(0, null);
      }
      if (l2.next != null) {
        l2 = l2.next;
      } else if (temp > 0) {
        l2 = new ListNode(0, null);
      }
    }
    for (int i = 0; i < result.size() - 1; i++) {
      result.get(i).next = result.get(i + 1);
    }
    if (result.size() == 0) {
      return new ListNode(l1.val + l2.val, null);
    }
    return result.get(0);
  }


  public static void main(String[] args) {
    // l1 = [2,4,3], l2 = [5,6,4]
    ListNode l1 = new ListNode(9, new ListNode(9,
        new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, null)))))));

    ListNode l2 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, null))));

//    addTwoNumbers(l1, l2);
    addTwoNumbers(new ListNode(0, null), new ListNode(1, null));


  }


}