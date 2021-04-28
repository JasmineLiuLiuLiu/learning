package org.jasmineliuliuliu.learning.designpattern.a1.factory;

public class A2FactoryMethodTest {

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

  public interface Factory {

    public Product newProduct();
  }

  public static class Factory1 implements Factory {

    @Override
    public Product newProduct() {
      return new ProductA();
    }
  }

  public static class Factory2 implements Factory {

    @Override
    public Product newProduct() {
      return new ProductB();
    }
  }

  public static void main(String[] args) {
    Factory factory1 = new Factory1();
    factory1.newProduct().show();
    Factory factory2 = new Factory2();
    factory2.newProduct().show();
  }
}
