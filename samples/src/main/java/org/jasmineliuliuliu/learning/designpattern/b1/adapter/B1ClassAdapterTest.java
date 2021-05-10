package org.jasmineliuliuliu.learning.designpattern.b1.adapter;

public class B1ClassAdapterTest {

  interface Target {

    void request();
  }

  public static class Adaptee {

    public void methodA() {
      System.out.println("This is methodA in Adaptee.");
    }
  }

  public static class Adapter extends Adaptee implements Target {

    @Override
    public void request() {
      methodA();
    }
  }

  public static void main(String[] args) {
    Target target = new Adapter();
    target.request();
  }

}
