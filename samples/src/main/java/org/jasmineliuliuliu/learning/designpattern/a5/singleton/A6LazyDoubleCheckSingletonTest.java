package org.jasmineliuliuliu.learning.designpattern.a5.singleton;

import java.util.stream.IntStream;

public class A6LazyDoubleCheckSingletonTest {

  public static class Singleton {

    private volatile static Singleton INSTANCE;

    private Singleton() {
    }

    public static Singleton getInstance() {
      if (INSTANCE == null) {
        synchronized (Singleton.class) {
          if (INSTANCE == null) {
            INSTANCE = new Singleton();
          }
        }
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
