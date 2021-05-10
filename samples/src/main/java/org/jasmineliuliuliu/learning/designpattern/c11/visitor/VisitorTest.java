package org.jasmineliuliuliu.learning.designpattern.c11.visitor;

public class VisitorTest {

  interface Element {

    void accept(Visitor visitor);
  }

  interface Visitor {

    void visit(ConcreteElement1 ce1);

    void visit(ConcreteElement2 ce2);
  }

  static class ConcreteElement1 implements Element {

    @Override
    public void accept(Visitor visitor) {
      visitor.visit(this);
    }

    private String specificMethod() {
      return "This is the specificMethod in ConcreteElement 1.";
    }
  }


  static class ConcreteElement2 implements Element {

    @Override
    public void accept(Visitor visitor) {
      visitor.visit(this);
    }

    private String myMethod() {
      return "This is the myMethod in ConcreteElement 2.";
    }
  }

  static class ConcreteVisitor implements Visitor {

    @Override
    public void visit(ConcreteElement1 ce1) {
      System.out.println(ce1.specificMethod());
    }

    @Override
    public void visit(ConcreteElement2 ce2) {
      System.out.println(ce2.myMethod());
    }
  }

  public static void main(String[] args) {
    System.out.println();
    Visitor visitor = new ConcreteVisitor();
    visitor.visit(new ConcreteElement1());
    visitor.visit(new ConcreteElement2());
  }
}
