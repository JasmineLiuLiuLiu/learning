package org.jasmineliuliuliu.learning.basic;

public class CompareForTheSameStringTests {

  public static void main(String[] args) {

    String a1 = "aaa";
    String a2 = "aaa";
    System.out.println("test 1: " + (a1 == a2)); //true

    String b1 = new String("bbb");
    String b2 = new String("bbb");
    System.out.println("test 2: " + (b1 == b2)); //false

    String c1 = "cccCCC";
    String c2 = "ccc" + "CCC";
    System.out.println("test 3: " + (c1 == c2)); //true

    String d1 = "dddDDD";
    String d2 = "ddd" + new String("DDD");
    System.out.println("test 4: " + (d1 == d2)); //false

    System.out.println("test 4.1: " + (d1.intern() == d2.intern())); //false
    String e1 = new String("ee");
    String e2 = "e" + new String("e");
    System.out.println("test 5: " + (e1 == e2)); //false

    String f1 = "f";
    String f2 = "f";
    String f3 = f1 + f2;
    System.out.println("test 6: " + (f3 == "ff")); //false

    String g1 = "g1";
    String g2 = "g" + 1;
    System.out.println("test 7: " + (g1 == g2)); //true

    String h1 = "htrue";
    String h2 = "h" + true;
    System.out.println("test 8: " + (h1 == h2)); //true

    String i1 = "i3.4";
    String i2 = "i" + 3.4;
    System.out.println("test 9: " + (i1 == i2)); //true

    String j1 = "jj";
    String j2 = "j";
    String j3 = "j" + j2;
    System.out.println("test 10: " + (j3 == j1)); //false

    String k1 = "kk";
    final String k2 = "k";
    String k3 = "k" + k2;
    System.out.println("test 11: " + (k3 == k1)); //true

    String l1 = "ll";
    final String l2 = getL2();
    String l3 = "l" + l2;
    System.out.println("test 12: " + (l3 == l1)); //false

    String m1 = "Jo";
    String m2 = m1 + "hn";
    System.out.println("test 13: " + (m2.intern() == m2)); //true

    String n1 = "ja";
    String n2 = n1 + "va";
    System.out.println("test 14: " + (n2.intern() == n2)); //false

  }

  static final String getL2() {
    return "l";
  }
}