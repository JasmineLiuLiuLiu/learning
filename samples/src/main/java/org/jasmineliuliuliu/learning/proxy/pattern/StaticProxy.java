package org.jasmineliuliuliu.learning.proxy.pattern;

import org.jasmineliuliuliu.learning.proxy.model.RealSubject;
import org.jasmineliuliuliu.learning.proxy.model.Subject;

public class StaticProxy implements Subject {

  private final Subject realSubject;

  public StaticProxy(Subject realSubject) {
    this.realSubject = realSubject;
  }

  @Override
  public void methodA() {
    System.out.println("static proxy: before");
    realSubject.methodA();
  }

  @Override
  public void methodB() {
    System.out.println("static proxy: before");
    realSubject.methodB();

  }

  public static void main(String[] args) {
    Subject subject = new StaticProxy(new RealSubject());
    subject.methodA();
    subject.methodB();
  }
}
