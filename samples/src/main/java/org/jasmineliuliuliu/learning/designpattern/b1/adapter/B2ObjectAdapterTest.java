package org.jasmineliuliuliu.learning.designpattern.b1.adapter;

public class B2ObjectAdapterTest {

  interface Target {

    void request();
  }

  public static class Adaptee {

    public void methodA() {
      System.out.println("This is methodA in Adaptee.");
    }
  }

  public static class Adapter implements Target {

    private final Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
      this.adaptee = adaptee;
    }

    @Override
    public void request() {
      adaptee.methodA();
    }
  }

  public static void main(String[] args) {
    Target target = new Adapter(new Adaptee());
    target.request();
  }
}
