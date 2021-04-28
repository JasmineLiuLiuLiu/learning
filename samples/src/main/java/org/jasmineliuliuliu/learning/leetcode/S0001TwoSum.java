package org.jasmineliuliuliu.learning.leetcode;

/**
 * Given an array of integers nums?and an integer target, return indices of the two numbers such
 * that they add up to target.
 * <p>
 * You may assume that each input would have exactly one solution, and you may not use the same
 * element twice.
 * <p>
 * You can return the answer in any order.
 * <p>
 * <b>Example 1:</b>
 * <p>
 * Input: nums = [2,7,11,15], target = 9
 * <p>
 * Output: [0,1]
 * <p>
 * Output: Because nums[0] + nums[1] == 9, we return [0, 1].
 * <p>
 * <b>Example 2:</b>
 * <p>
 * Input: nums = [3,2,4], target = 6
 * <p>
 * Output: [1,2]
 * <p>
 * <b>Example 3:</b>
 * <p>
 * Input: nums = [3,3], target = 6
 * <p>
 * Output: [0,1]
 * <p>
 * <p>
 * <b>Constraints:</b>
 * <li>2 <= nums.length <= 103
 * <li>-109 <= nums[i] <= 109
 * <li>-109 <= target <= 109
 * <p>
 * Only one valid answer exists.
 */
public class S0001TwoSum {

  public int[] twoSum(int[] nums, int target) {
    for (int i = 0; i < nums.length - 1; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) {
          return new int[]{i, j};
        }
      }
    }
    return new int[]{-1, -1};
  }

  public static void main(String[] args) {
    S0001TwoSum solution = new S0001TwoSum();

    // Example 1: [0, 1]
    int[] i1 = solution.twoSum(new int[]{2, 7, 11, 15}, 9);
    System.out.println("Example 1: [" + i1[0] + ", " + i1[1] + "]");

    // Example 2: [1, 2]
    int[] i2 = solution.twoSum(new int[]{3, 2, 4}, 6);
    System.out.println("Example 2: [" + i2[0] + ", " + i2[1] + "]");

    // Example 3: [0, 1]
    int[] i3 = solution.twoSum(new int[]{3, 3}, 6);
    System.out.println("Example 3: [" + i3[0] + ", " + i3[1] + "]");

  }
}
