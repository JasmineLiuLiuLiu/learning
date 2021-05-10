package org.jasmineliuliuliu.learning.designpattern.b4.decorator;

public class DecoratorTest {

  interface Component {

    void operation();
  }

  static class ConcreteComponent implements Component {

    public ConcreteComponent() {
      System.out.println("This is ConcreteComponent.");

    }

    @Override
    public void operation() {
      System.out.println("I am doing some operations....");
    }
  }

  static class Decorator implements Component {

    private final Component component;

    public Decorator(Component component) {
      this.component = component;
    }

    @Override
    public void operation() {
      component.operation();
    }

  }

  static class ConcreteDecorator1 extends Decorator {

    ConcreteDecorator1(Component component) {
      super(component);
      System.out.println("This is ConcreteDecorator 1.");
    }

    @Override
    public void operation() {
      addSomethingBefore();
      super.operation();
    }

    void addSomethingBefore() {
      System.out.println("*************************");
    }
  }

  static class ConcreteDecorator2 extends Decorator {

    ConcreteDecorator2(Component component) {
      super(component);
      System.out.println("This is ConcreteDecorator 2.");
    }

    @Override
    public void operation() {
      super.operation();
      addSomethingAfter();
    }

    void addSomethingAfter() {
      System.out.println("###########################");
    }
  }

  public static void main(String[] args) {

    System.out.println();
    Component component = new ConcreteComponent();
    component.operation();

    System.out.println();

    Component concreteDecorator1 = new ConcreteDecorator1(component);
    concreteDecorator1.operation();

    System.out.println();

    Component concreteDecorator2 = new ConcreteDecorator2(component);
    concreteDecorator2.operation();
  }
}
