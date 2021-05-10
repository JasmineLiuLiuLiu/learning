package org.jasmineliuliuliu.learning.designpattern.c5.strategy;

public class StrategyTest {

  interface Strategy {

    void strategyMethod();
  }

  static class Context {

    private Strategy strategy;

    public Strategy getStrategy() {
      return strategy;
    }

    public void setStrategy(Strategy strategy) {
      this.strategy = strategy;
    }

    public void strategyMethod() {
      strategy.strategyMethod();
    }
  }

  static class ConcreteStrategy1 implements Strategy {

    @Override
    public void strategyMethod() {
      System.out.println("ConcreteStrategy1's method is called.");
    }
  }

  static class ConcreteStrategy2 implements Strategy {

    @Override
    public void strategyMethod() {
      System.out.println("ConcreteStrategy2's method is called.");
    }
  }

  public static void main(String[] args) {
    System.out.println();
    Context context = new Context();
    context.setStrategy(new ConcreteStrategy1());
    context.strategyMethod();
    System.out.println();
    context.setStrategy(new ConcreteStrategy2());
    context.strategyMethod();
  }

}
