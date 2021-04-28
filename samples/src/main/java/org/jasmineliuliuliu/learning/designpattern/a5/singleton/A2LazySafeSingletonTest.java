package org.jasmineliuliuliu.learning.designpattern.a5.singleton;

import java.util.stream.IntStream;

public class A2LazySafeSingletonTest {

  public static class Singleton {

    private static Singleton INSTANCE;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
      if (INSTANCE == null) {
        INSTANCE = new Singleton();
      }
      return INSTANCE;
    }

    public void show() {
      System.out.println("This is INSTANCE " + INSTANCE.hashCode());
    }
  }

  public static void main(String[] args) {
    IntStream.range(0, 20).forEach(i -> {
      Singleton instance = Singleton.getInstance();
      instance.show();
    });

    Singleton instance2 = Singleton.getInstance();
    instance2.show();
  }
}
