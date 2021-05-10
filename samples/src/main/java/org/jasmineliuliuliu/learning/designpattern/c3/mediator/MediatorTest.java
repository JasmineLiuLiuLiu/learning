package org.jasmineliuliuliu.learning.designpattern.c3.mediator;

import java.util.ArrayList;
import java.util.List;

public class MediatorTest {

  static abstract class Colleague {

    private Mediator mediator;

    public Mediator getMediator() {
      return mediator;
    }

    public void setMediator(Mediator mediator) {
      this.mediator = mediator;
    }

    public abstract void receive();

    public abstract void send();
  }

  static abstract class Mediator {

    public abstract void register(Colleague colleague);

    public abstract void relay(Colleague colleague);
  }

  public static class ConcreteMediator extends Mediator {

    List<Colleague> colleagues = new ArrayList<>();

    @Override
    public void register(Colleague colleague) {
      if (!colleagues.contains(colleague)) {
        colleagues.add(colleague);
        colleague.setMediator(this);
      }
    }

    @Override
    public void relay(Colleague colleague) {
      colleagues.forEach(c -> {
        if (!c.equals(colleague)) {
          c.receive();
        }
      });
    }
  }

  public static class ConcreteColleague1 extends Colleague {

    @Override
    public void receive() {
      System.out.println("ConcreteColleague1 receives this request.");
    }

    @Override
    public void send() {
      System.out.println("ConcreteColleague1 sends this request.");
      getMediator().relay(this);
    }
  }

  public static class ConcreteColleague2 extends Colleague {

    @Override
    public void receive() {
      System.out.println("ConcreteColleague2 receives this request.");
    }

    @Override
    public void send() {
      System.out.println("ConcreteColleague2 sends this request.");
      getMediator().relay(this);
    }
  }

  public static void main(String[] args) {
    Colleague colleague1 = new ConcreteColleague1();
    Colleague colleague2 = new ConcreteColleague2();

    Mediator mediator = new ConcreteMediator();
    mediator.register(colleague1);
    mediator.register(colleague2);
    System.out.println();
    colleague1.send();
    System.out.println();
    colleague2.send();
  }
}
