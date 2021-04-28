package org.jasmineliuliuliu.learning.leetcode;

/**
 * Given a string s, return the longest palindromic substring in s.
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: s = "babad"
 * <p>
 * Output: "bab"
 * <p>
 * Note: "aba" is also a valid answer.
 * <p>
 * Example 2:
 * <p>
 * Input: s = "cbbd"
 * <p>
 * Output: "bb"
 * <p>
 * Example 3:
 * <p>
 * Input: s = "a"
 * <p>
 * Output: "a"
 * <p>
 * Example 4:
 * <p>
 * Input: s = "ac"
 * <p>
 * Output: "a"
 * <p>
 * Constraints:
 *
 * <li>1 <= s.length <= 1000
 * <li>s consist of only digits and English letters (lower-case and/or upper-case),
 */
public class S0005LongestPalindromicSubstring {

  public String longestPalindrome(String s) {
    if (s.length() < 2) {
      return s;
    }

    char[] chars = s.toCharArray();
    String[] eStrings = new String[chars.length * 2 + 1];
    for (int i = 0; i < chars.length; i++) {
      eStrings[i * 2] = "";
      eStrings[i * 2 + 1] = String.valueOf(chars[i]);
    }
    eStrings[chars.length * 2] = "";

    String longestPalindromes = String.valueOf(chars[0]);
    int maxLength = longestPalindromes.length();

    for (int i = 0; i < eStrings.length; i++) {
      String palindrome = eStrings[i];
      for (int left = i - 1, right = i + 1; left >= 0 && right < eStrings.length; left--, right++) {
        if (eStrings[right].equals(eStrings[left])) {
          palindrome = eStrings[left] + palindrome + eStrings[right];
          if (palindrome.length() > maxLength) {
            longestPalindromes = palindrome;
            maxLength = palindrome.length();
          }
        } else {
          break;
        }
      }
    }

    return longestPalindromes;

  }

  public static void main(String[] args) {
    S0005LongestPalindromicSubstring solution = new S0005LongestPalindromicSubstring();

    // Example 1: bab
    System.out.println("Example 1: " + solution.longestPalindrome("babad"));

    // Example 2: bb
    System.out.println("Example 2: " + solution.longestPalindrome("cbbd"));

    // Example 3: a
    System.out.println("Example 3: " + solution.longestPalindrome("a"));

    // Example 4: a
    System.out.println("Example 4: " + solution.longestPalindrome("ac"));

    // Example 5: bb
    System.out.println("Example 5: " + solution.longestPalindrome("bb"));
  }
}
