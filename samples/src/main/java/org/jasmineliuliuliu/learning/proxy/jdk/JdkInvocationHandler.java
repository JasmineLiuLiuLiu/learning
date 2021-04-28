package org.jasmineliuliuliu.learning.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.jasmineliuliuliu.learning.proxy.model.RealSubject;
import org.jasmineliuliuliu.learning.proxy.model.Subject;

public class JdkInvocationHandler implements InvocationHandler {

  private final RealSubject realSubject;

  public JdkInvocationHandler(RealSubject realSubject) {
    this.realSubject = realSubject;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("before");
    Object result = null;
    try {
      result = method.invoke(realSubject, args);
    } catch (Exception ex) {
      System.out.println("exception: " + ex.getMessage());
      throw ex;
    } finally {
      System.out.println("after");
    }
    return result;
  }

  public static void main(String[] args) {
    Subject subject = (Subject) Proxy
        .newProxyInstance(JdkInvocationHandler.class.getClassLoader(), new Class[]{Subject.class},
            new JdkInvocationHandler(new RealSubject()));
    subject.methodA();
    subject.methodB();
  }
}
