package org.jasmineliuliuliu.learning.designpattern.a4.builder;

import lombok.Getter;
import lombok.Setter;

public class builderTest {

  @Setter
  @Getter
  public static class Product {

    private String partA;
    private String partB;
    private String partC;

    public void show() {
      System.out.println(
          "Part A is: [" + partA + "], Part B is: [" + partB + "], Part C is: [" + partC + "]");
    }
  }

  public static abstract class Builder {

    protected Product product = new Product();

    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();

    public Product product() {
      return product;
    }
  }

  public static class Builder1 extends Builder {

    @Override
    public void buildPartA() {
      product.setPartA("1-A");
    }

    @Override
    public void buildPartB() {
      product.setPartB("1-B");
    }

    @Override
    public void buildPartC() {
      product.setPartC("1-C");
    }
  }

  public static class Builder2 extends Builder {

    @Override
    public void buildPartA() {
      product.setPartA("2-A");
    }

    @Override
    public void buildPartB() {
      product.setPartB("2-B");
    }

    @Override
    public void buildPartC() {
      product.setPartC("2-C");
    }
  }

  public static class Director {

    private final Builder builder;

    public Director(
        Builder builder) {
      this.builder = builder;
    }

    public Product build() {
      builder.buildPartA();
      builder.buildPartB();
      builder.buildPartC();
      return builder.product();
    }
  }

  public static void main(String[] args) {
    Builder builder1 = new Builder1();
    Director director1 = new Director(builder1);
    director1.build().show();

    Builder builder2 = new Builder2();
    Director director2 = new Director(builder2);
    director2.build().show();
  }
}
