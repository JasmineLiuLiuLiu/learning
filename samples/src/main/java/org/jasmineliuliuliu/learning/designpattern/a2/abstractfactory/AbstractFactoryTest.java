package org.jasmineliuliuliu.learning.designpattern.a2.abstractfactory;

public class AbstractFactoryTest {

  public interface ProductA {

    void show();
  }

  public static class ProductA1 implements ProductA {

    @Override
    public void show() {
      System.out.println("Product A1");
    }
  }

  public static class ProductA2 implements ProductA {

    @Override
    public void show() {
      System.out.println("Product A2");
    }
  }

  public interface ProductB {

    void show();
  }

  public static class ProductB1 implements ProductB {

    @Override
    public void show() {
      System.out.println("Product B1");
    }
  }

  public static class ProductB2 implements ProductB {

    @Override
    public void show() {
      System.out.println("Product B2");
    }
  }

  public interface AbstractFactory {

    ProductA ProductA();

    ProductB ProductB();
  }

  public static class Factory1 implements AbstractFactory {

    @Override
    public ProductA ProductA() {
      return new ProductA1();
    }

    @Override
    public ProductB ProductB() {
      return new ProductB1();
    }
  }

  public static class Factory2 implements AbstractFactory {

    @Override
    public ProductA ProductA() {
      return new ProductA2();
    }

    @Override
    public ProductB ProductB() {
      return new ProductB2();
    }
  }

  public static void main(String[] args) {
    AbstractFactory factory1 = new Factory1();
    factory1.ProductA().show();
    factory1.ProductB().show();

    AbstractFactory factory2 = new Factory2();
    factory2.ProductA().show();
    factory2.ProductB().show();

  }
}
