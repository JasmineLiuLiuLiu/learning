package org.jasmineliuliuliu.learning.designpattern.a4.prototype;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class A1PrototypeTest {

  public static class Prototype implements Cloneable {

    private String name;

    private Set<String> set = new HashSet<>();

    public Prototype(String name, String... var) {
      this.name = name;
      Arrays.stream(var).sequential().forEach(v -> set.add(v));
    }

    public void setName(String name) {
      this.name = name;
    }

    public void addVar(String var) {
      set.add(var);
    }

    public void show() {
      System.out.println("Name is: " + name + ", the set contains following values: " + set.stream()
          .map(v -> v.toString()).collect(Collectors.joining(", ")));
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }


  public static void main(String[] args) throws CloneNotSupportedException {
    Prototype prototype = new Prototype("Jasmine Liu", "Java", "Python", "Go");
    Prototype cloned = (Prototype) prototype.clone();
    System.out.print("The proto object is: ");
    prototype.show();
    System.out.print("The cloned object is: ");
    cloned.show();

    System.out.println("Change cloned object name and set...................");
    cloned.setName("Justin Ye");
    cloned.addVar("Javascript");
    System.out.print("The proto object is: ");
    prototype.show();
    System.out.print("The cloned object is: ");
    cloned.show();

  }

}
