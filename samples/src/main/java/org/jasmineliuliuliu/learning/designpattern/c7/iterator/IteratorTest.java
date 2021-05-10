package org.jasmineliuliuliu.learning.designpattern.c7.iterator;

import java.util.ArrayList;
import java.util.List;

public class IteratorTest {

  interface Iterator {

    Object first();

    Object next();

    boolean hasNext();
  }

  interface Aggregate {

    void add(Object o);

    void remove(Object o);

    Iterator getIterator();
  }

  static class ConcreteIterator implements Iterator {

    private List<Object> list = null;
    private int index = -1;

    ConcreteIterator(List<Object> list) {
      this.list = list;
    }

    @Override
    public Object first() {
      index = 0;
      return list.get(index);
    }

    @Override
    public Object next() {
      Object o = null;
      if (hasNext()) {
        o = list.get(++index);
      }
      return o;
    }

    @Override
    public boolean hasNext() {
      return index < list.size() - 1;
    }
  }

  static class ConcreteAggregate implements Aggregate {

    List<Object> list = new ArrayList<>();

    @Override
    public void add(Object o) {
      list.add(o);
    }

    @Override
    public void remove(Object o) {
      list.remove(o);
    }

    @Override
    public Iterator getIterator() {
      return new ConcreteIterator(list);
    }
  }

  public static void main(String[] args) {
    Aggregate aggregate = new ConcreteAggregate();
    aggregate.add("Jasmine");
    aggregate.add("Justin");
    aggregate.add("Joyce");
    System.out.println();
    Iterator iterator = aggregate.getIterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }

  }
}
