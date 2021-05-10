package org.jasmineliuliuliu.learning.designpattern.b2.bridge;

public class BridgeTest {

  interface Implementor {

    void operate();
  }

  static class Implementor1 implements Implementor {

    @Override
    public void operate() {
      System.out.println("Implementor1 operates.");
    }
  }

  static class Implementor2 implements Implementor {

    @Override
    public void operate() {
      System.out.println("Implementor2 operates.");
    }
  }

  static abstract class Abstraction {

    protected final Implementor implementor;

    public Abstraction(Implementor implementor) {
      this.implementor = implementor;
    }

    public abstract void Operation();
  }

  static class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(Implementor implementor) {
      super(implementor);
    }

    @Override
    public void Operation() {
      implementor.operate();
    }
  }

  public static void main(String[] args) {
    Abstraction abstraction1 = new RefinedAbstraction(new Implementor1());
    abstraction1.Operation();

    Abstraction abstraction2 = new RefinedAbstraction(new Implementor2());
    abstraction2.Operation();
  }
}
