package org.jasmineliuliuliu.learning.designpattern.a1.factory;

public class A1SimpleFactoryTest {

  public interface Product {

    void show();
  }

  public static class ProductA implements Product {

    @Override
    public void show() {
      System.out.println("Product A");
    }
  }

  public static class ProductB implements Product {

    @Override
    public void show() {
      System.out.println("Product B");
    }
  }

  static class SimpleFactory {

    public static Product newProduct(String kind) {
      switch (kind) {
        case "A":
          return new ProductA();
        case "B":
          return new ProductB();
      }
      return null;
    }
  }

  public static void main(String[] args) {
    SimpleFactory.newProduct("A").show();
    SimpleFactory.newProduct("B").show();
  }

}
