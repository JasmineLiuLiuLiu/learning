package org.jasmineliuliuliu.learning.designpattern.a5.singleton;

import java.util.stream.IntStream;

public class A7EagerEnumSingletonTest {

  public enum Singleton {
    INSTANCE;
  }

  public static void main(String[] args) {
    IntStream.range(0, 20).forEach(i -> {
      Singleton instance1 = Singleton.INSTANCE;
      System.out.println("This is INSTANCE " + instance1.hashCode());
    });

    Singleton instance2 = Singleton.INSTANCE;
    System.out.println("This is INSTANCE " + instance2.hashCode());
  }
}
