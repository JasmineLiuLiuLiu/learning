package org.jasmineliuliuliu.learning.designpattern.c10.template;

public class TemplateMethodTest {

  abstract static class AbstractClass {

    void templateMethod() {
      specificMethod();
      abstractMethod1();
      abstractMethod2();
    }

    void specificMethod() {
      System.out.println("This is the specific method in abstract class.");
    }

    abstract void abstractMethod1();

    abstract void abstractMethod2();
  }

  static class ConcreteClass extends AbstractClass {

    @Override
    void abstractMethod1() {
      System.out.println("This is the abstract method 1 in concrete class.");
    }

    @Override
    void abstractMethod2() {
      System.out.println("This is the abstract method 2 in concrete class.");

    }
  }

  public static void main(String[] args) {
    AbstractClass clazz = new ConcreteClass();
    System.out.println();
    clazz.templateMethod();
  }

}
