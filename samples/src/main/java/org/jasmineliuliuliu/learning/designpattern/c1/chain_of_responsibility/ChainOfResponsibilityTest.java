package org.jasmineliuliuliu.learning.designpattern.c1.chain_of_responsibility;

public class ChainOfResponsibilityTest {

  public static abstract class Handler {

    private Handler next;

    public Handler getNext() {
      return next;
    }

    public void setNext(
        Handler next) {
      this.next = next;
    }

    abstract void handleRequest(String request);
  }

  public static class HandlerOne extends Handler {

    @Override
    void handleRequest(String request) {
      if (request.equalsIgnoreCase("ONE")) {
        System.out.println("HandlerOne handlers this request.");
      } else if (getNext() != null) {
        getNext().handleRequest(request);
      } else {
        System.out.println("Cannot handler this request.");
      }
    }
  }

  public static class HandlerTwo extends Handler {

    @Override
    void handleRequest(String request) {
      if (request.equalsIgnoreCase("TWO")) {
        System.out.println("HandlerOne handlers this request.");
      } else if (getNext() != null) {
        getNext().handleRequest(request);
      } else {
        System.out.println("Cannot handler this request.");
      }
    }
  }

  public static void main(String[] args) {
    Handler handler = new HandlerOne();
    handler.setNext(new HandlerTwo());
    System.out.println();
    handler.handleRequest("ONE");
    System.out.println();
    handler.handleRequest("TWO");
    System.out.println();
    handler.handleRequest("THREE");
  }
}
