package org.jasmineliuliuliu.learning.designpattern.c9.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverTest {

  interface Observer {

    void response();
  }

  static class Observer1 implements Observer {

    @Override
    public void response() {
      System.out.println("Observer1 responses.");
    }
  }

  static class Observer2 implements Observer {

    @Override
    public void response() {
      System.out.println("Observer2 responses.");
    }
  }

  static abstract class Subject {

    List<Observer> observers = new ArrayList<>();

    abstract void add(Observer o);

    abstract void remove(Observer o);

    abstract void notifyObserver();
  }

  static class Subject1 extends Subject {

    @Override
    void add(Observer o) {
      observers.add(o);
    }

    @Override
    void remove(Observer o) {
      observers.remove(o);
    }

    @Override
    void notifyObserver() {
      observers.forEach(o -> o.response());
    }
  }

  public static void main(String[] args) {
    System.out.println();
    Subject s = new Subject1();
    s.add(new Observer1());
    s.add(new Observer2());
    s.notifyObserver();
  }
}
