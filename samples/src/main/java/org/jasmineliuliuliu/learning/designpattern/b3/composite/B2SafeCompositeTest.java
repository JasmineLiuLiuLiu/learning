package org.jasmineliuliuliu.learning.designpattern.b3.composite;

import java.util.ArrayList;
import java.util.List;

public class B2SafeCompositeTest {

  interface Component {

    void operation();
  }

  static class Leaf implements Component {

    private final String name;

    public Leaf(String name) {
      this.name = name;
    }

    @Override
    public void operation() {
      System.out.println("The leaf's name is: " + name);
    }
  }

  static class Composite implements Component {

    private List<Component> children = new ArrayList<>();

    public void add(Component c) {
      children.add(c);
    }

    public void remove(Component c) {
      children.remove(c);
    }

    public Component getChild(int i) {
      return null;
    }

    @Override
    public void operation() {
      children.forEach(c -> c.operation());
    }
  }

  public static void main(String[] args) {
    Component leaf1 = new Leaf("Node1");
    Component leaf2 = new Leaf("Node2");
    Component leaf3 = new Leaf("Node3");

    Composite c0 = new Composite();
    Composite c1 = new Composite();
    c0.add(leaf1);
    c0.add(c1);
    c1.add(leaf2);
    c1.add(leaf3);
    c0.operation();
  }
}
