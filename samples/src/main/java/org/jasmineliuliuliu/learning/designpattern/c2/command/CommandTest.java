package org.jasmineliuliuliu.learning.designpattern.c2.command;

public class CommandTest {

  interface Command {

    void execute();
  }

  interface Receiver {

    void action();
  }

  static class Invoker {

    private Command command;

    public Invoker(Command command) {
      this.command = command;
    }

    public Command getCommand() {
      return command;
    }

    public void setCommand(
        Command command) {
      this.command = command;
    }

    void invoke() {
      command.execute();
    }
  }

  static class Receiver1 implements Receiver {

    @Override
    public void action() {
      System.out.println("Receiver1 do the action.");
    }
  }

  static class Receiver2 implements Receiver {

    @Override
    public void action() {
      System.out.println("Receiver2 do the action.");
    }
  }

  static class Command1 implements Command {

    private final Receiver receiver;

    public Command1() {
      this.receiver = new Receiver1();
    }

    @Override
    public void execute() {
      receiver.action();
    }
  }

  static class Command2 implements Command {

    private final Receiver receiver;

    public Command2() {
      this.receiver = new Receiver2();
    }

    @Override
    public void execute() {
      receiver.action();
    }
  }

  public static void main(String[] args) {
    Invoker invoker = new Invoker(new Command1());
    System.out.println();
    invoker.invoke();
    invoker.setCommand(new Command2());
    System.out.println();
    invoker.invoke();
  }
}
