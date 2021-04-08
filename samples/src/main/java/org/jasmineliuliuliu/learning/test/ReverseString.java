package org.jasmineliuliuliu.learning.test;

public class ReverseString {

  public static void main(String[] args) {
    String a = "abcdefg";
    char[] b = a.toCharArray();
    char[] rb = new char[b.length];
    for (int i = 0; i < b.length; i++) {
      rb[b.length - 1 - i] = b[i];
    }
    String ra = new String(rb);
    System.out.println(ra);
  }
}
