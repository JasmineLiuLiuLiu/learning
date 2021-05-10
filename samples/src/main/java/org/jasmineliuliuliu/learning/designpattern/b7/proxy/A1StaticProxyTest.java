package org.jasmineliuliuliu.learning.designpattern.b7.proxy;

public class A1StaticProxyTest {

  interface Subject {

    void test();
  }

  static class RealSubject implements Subject {

    @Override
    public void test() {
      System.out.println("This is the realSubject.");
    }
  }

  static class Proxy implements Subject {

    private Subject subject = new RealSubject();

    @Override
    public void test() {
      preTest();
      subject.test();
      postTest();
    }

    protected void preTest() {
      System.out.println("This is preTest in Proxy.");
    }

    protected void postTest() {
      System.out.println("This is postTest in Proxy.");
    }
  }

  public static void main(String[] args) {
    Proxy proxy = new Proxy();
    proxy.test();
  }

}
