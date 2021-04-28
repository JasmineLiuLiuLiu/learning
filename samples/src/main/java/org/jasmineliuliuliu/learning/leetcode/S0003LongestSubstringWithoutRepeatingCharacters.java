package org.jasmineliuliuliu.learning.leetcode;

/**
 * Given a string s, find the length of the longest substring without repeating characters.
 * <p>
 * Example 1:
 * <p>
 * Input: s = "abcabcbb"
 * <p>
 * Output: 3
 * <p>
 * Explanation: The answer is "abc", with the length of 3.
 * <p>
 * Example 2:
 * <p>
 * Input: s = "bbbbb"
 * <p>
 * Output: 1
 * <p>
 * Explanation: The answer is "b", with the length of 1.
 * <p>
 * Example 3:
 * <p>
 * Input: s = "pwwkew"
 * <p>
 * Output: 3
 * <p>
 * Explanation: The answer is "wke", with the length of 3.
 * <p>
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * <p>
 * Example 4:
 * <p>
 * Input: s = ""
 * <p>
 * Output: 0
 * <p>
 * Constraints:
 *
 * <li>0 <= s.length <= 5 * 104
 * <li>s consists of English letters, digits, symbols and spaces.
 */
public class S0003LongestSubstringWithoutRepeatingCharacters {

  public int lengthOfLongestSubstring(String s) {
    char[] chars = s.toCharArray();
    int maxLength = 0;
    for (int i = 0; i < chars.length; i++) {
      StringBuilder s1 = new StringBuilder();
      s1.append(chars[i]);
      for (int j = i + 1; j < chars.length; j++) {
        if (s1.toString().contains(String.valueOf(chars[j]))) {
          break;
        }
        s1.append(chars[j]);
      }
      if (maxLength < s1.length()) {
        maxLength = s1.length();
      }
    }
    return maxLength;
  }

  public static void main(String[] args) {
    S0003LongestSubstringWithoutRepeatingCharacters solution = new S0003LongestSubstringWithoutRepeatingCharacters();

    //Example 1: 3
    System.out.println("Example 1: " + solution.lengthOfLongestSubstring("abcabcbb"));

    //Example 2: 1
    System.out.println("Example 2: " + solution.lengthOfLongestSubstring("bbbbb"));

    //Example 3: 3
    System.out.println("Example 3: " + solution.lengthOfLongestSubstring("pwwkew"));

    //Example 4: 0
    System.out.println("Example 4: " + solution.lengthOfLongestSubstring(""));
  }
}
