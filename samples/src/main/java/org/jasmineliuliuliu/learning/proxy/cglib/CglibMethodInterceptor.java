package org.jasmineliuliuliu.learning.proxy.cglib;

import java.lang.reflect.Method;
import org.jasmineliuliuliu.learning.proxy.model.RealSubject;
import org.jasmineliuliuliu.learning.proxy.model.Subject;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibMethodInterceptor implements MethodInterceptor {

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
      throws Throwable {
    System.out.println("before");
    Object result = null;
    try {
      result = methodProxy.invokeSuper(o, objects);
    } catch (Exception ex) {
      System.out.println("ex: " + ex.getMessage());
      throw ex;
    } finally {
      System.out.println("after");
    }
    return result;
  }

  public static void main(String[] args) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(RealSubject.class);
    enhancer.setCallback(new CglibMethodInterceptor());
    Subject subject = (Subject) enhancer.create();
    subject.methodA();
    subject.methodB();
  }
}
