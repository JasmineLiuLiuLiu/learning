package org.jasmineliuliuliu.learning.leetcode;

import java.util.Arrays;

/**
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the
 * two sorted arrays.
 * <p>
 * Example 1:
 * <p>
 * Input: nums1 = [1,3], nums2 = [2]
 * <p>
 * Output: 2.00000
 * <p>
 * Explanation: merged array = [1,2,3] and median is 2.
 * <p>
 * Example 2:
 * <p>
 * Input: nums1 = [1,2], nums2 = [3,4]
 * <p>
 * Output: 2.50000
 * <p>
 * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 * <p>
 * Example 3:
 * <p>
 * Input: nums1 = [0,0], nums2 = [0,0]
 * <p>
 * Output: 0.00000
 * <p>
 * Example 4:
 * <p>
 * Input: nums1 = [], nums2 = [1]
 * <p>
 * Output: 1.00000
 * <p>
 * Example 5:
 * <p>
 * Input: nums1 = [2], nums2 = []
 * <p>
 * Output: 2.00000
 * <p>
 * Constraints:
 *
 * <li>nums1.length == m
 * <li>nums2.length == n
 * <li>0 <= m <= 1000
 * <li>0 <= n <= 1000
 * <li>1 <= m + n <= 2000
 * <li>-10^6 <= nums1[i], nums2[i] <= 10^6
 * <p>
 * Follow up: The overall run time complexity should be O(log (m+n)).
 */
public class S0004MedianOfTwoSortedArrays {

  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    double median = 0;
    int[] nums = new int[nums1.length + nums2.length];
    for (int i = 0; i < nums1.length; i++) {
      nums[i] = nums1[i];
    }
    for (int j = 0; j < nums2.length; j++) {
      nums[nums1.length + j] = nums2[j];
    }
    Arrays.sort(nums);
    if (nums.length % 2 == 0) {
      median = (nums[nums.length / 2 - 1] + nums[nums.length / 2]) / 2d;
    } else {
      median = nums[nums.length / 2];
    }
    return median;
  }

  public static void main(String[] args) {
    S0004MedianOfTwoSortedArrays solution = new S0004MedianOfTwoSortedArrays();

    //Example 1: 2.0000
    double i1 = solution.findMedianSortedArrays(new int[]{1, 3}, new int[]{2});
    System.out.println("Example 1: " + String.format("%.4f", i1));

    //Example 2: 2.5000
    double i2 = solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4});
    System.out.println("Example 2: " + String.format("%.4f", i2));

    //Example 3: 0.0000
    double i3 = solution.findMedianSortedArrays(new int[]{0, 0}, new int[]{0, 0});
    System.out.println("Example 3: " + String.format("%.4f", i3));

    //Example 4: 2.0000
    double i4 = solution.findMedianSortedArrays(new int[]{2}, new int[]{});
    System.out.println("Example 4: " + String.format("%.4f", i4));
  }
}
