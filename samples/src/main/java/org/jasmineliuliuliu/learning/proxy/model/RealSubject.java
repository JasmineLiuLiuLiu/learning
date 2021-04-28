package org.jasmineliuliuliu.learning.proxy.model;

public class RealSubject implements Subject {

  @Override
  public void methodA() {
    System.out.println("RealSubject methodA");
  }

  @Override
  public void methodB() {
    System.out.println("RealSubject methodB");
  }
}
