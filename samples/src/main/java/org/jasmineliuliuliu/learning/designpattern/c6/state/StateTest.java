package org.jasmineliuliuliu.learning.designpattern.c6.state;

public class StateTest {

  interface State {

    void handle(Context context);
  }

  static class Context {

    private State state;

    public Context() {
      state = new State1();
    }

    public State getState() {
      return state;
    }

    public void setState(State state) {
      this.state = state;
    }

    void handle() {
      state.handle(this);
    }

  }

  static class State1 implements State {

    @Override
    public void handle(Context context) {
      System.out.println("Current State is State1.");
      context.setState(new State2());
    }
  }

  static class State2 implements State {

    @Override
    public void handle(Context context) {
      System.out.println("Current State is State2.");
      context.setState(new State1());
    }
  }

  public static void main(String[] args) {
    System.out.println();
    Context context = new Context();
    context.handle();
    context.handle();

    context.handle();
    context.handle();

  }
}
