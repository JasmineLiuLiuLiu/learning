package org.jasmineliuliuliu.learning.designpattern.a3.prototype;

public class prototypeTest {

  public static class Prototype implements Cloneable {

    private String name;

    public Prototype(String name) {
      this.name = name;
    }

    public void show() {
      System.out.println("My name is: " + name);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

  public static void main(String[] args) throws CloneNotSupportedException {
    Prototype prototype = new Prototype("Jasmine Liu");
    prototype.show();
    Prototype cloned = (Prototype) prototype.clone();
    cloned.show();
  }

}
