package org.jasmineliuliuliu.learning.designpattern.a5.singleton;

import java.util.stream.IntStream;

public class A5LazyStaticInnerClassSingletonTest {

  public static class Singleton {

    private static class SingletonHolder {

      private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {
    }

    public static Singleton getInstance() {
      return SingletonHolder.INSTANCE;
    }

    public void show() {
      System.out.println("This is INSTANCE " + getInstance().hashCode());
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
