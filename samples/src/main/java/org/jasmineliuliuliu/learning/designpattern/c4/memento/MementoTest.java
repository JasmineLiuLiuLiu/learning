package org.jasmineliuliuliu.learning.designpattern.c4.memento;

public class MementoTest {

  static class Memento {

    private String state;

    public Memento(String state) {
      this.state = state;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

  }

  static class Originator {

    private String state;

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public Memento createMemento() {
      return new Memento(state);
    }

    public void restoreMemento(Memento memento) {
      state = memento.getState();
    }
  }

  static class CareTaker {

    private Memento memento;

    public Memento getMemento() {
      return memento;
    }

    public void setMemento(
        Memento memento) {
      this.memento = memento;
    }
  }

  public static void main(String[] args) {
    System.out.println();
    Originator originator = new Originator();
    originator.setState("State 0");
    System.out.println("Current State: " + originator.getState());
    CareTaker careTaker = new CareTaker();
    careTaker.setMemento(originator.createMemento());
    originator.setState("State 1");
    System.out.println("Current State: " + originator.getState());
    originator.restoreMemento(careTaker.getMemento());
    System.out.println("Current State: " + originator.getState());

  }
}
