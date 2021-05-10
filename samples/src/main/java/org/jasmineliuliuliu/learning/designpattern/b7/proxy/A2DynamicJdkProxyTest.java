package org.jasmineliuliuliu.learning.designpattern.b7.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class A2DynamicJdkProxyTest {

  interface Subject {

    void test();
  }

  static class RealSubject implements Subject {

    @Override
    public void test() {
      System.out.println("This is the realSubject.");
    }
  }

  static class DynamicProxy implements InvocationHandler {

    private final Subject subject;

    DynamicProxy(Subject subject) {
      this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      preInvoke();
      Object result = method.invoke(subject, args);
      postInvoke();
      return result;
    }

    protected void preInvoke() {
      System.out.println("Pre invoke in dynamic proxy.");
    }

    protected void postInvoke() {
      System.out.println("Post invoke in dynamic proxy.");
    }
  }

  public static void main(String[] args) {
    Subject realSubject = new RealSubject();
    InvocationHandler proxy = new DynamicProxy(realSubject);

    Subject subject = (Subject) Proxy
        .newProxyInstance(Subject.class.getClassLoader(), RealSubject.class.getInterfaces(), proxy);

    subject.test();
  }

}
