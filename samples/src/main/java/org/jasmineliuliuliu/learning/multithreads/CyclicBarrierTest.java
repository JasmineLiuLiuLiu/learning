package org.jasmineliuliuliu.learning.multithreads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import org.jasmineliuliuliu.learning.uitls.Utils;

public class CyclicBarrierTest {

  static class WorkThread extends Thread {

    private final CyclicBarrier cyclicBarrier;

    WorkThread(String name, CyclicBarrier cyclicBarrier) {
      super(name);
      this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
      while (!isInterrupted()) {
        try {
          Utils.printlnWithTimestamp(getName() + " is running...");
          Thread.sleep(3000);
          Utils.printlnWithTimestamp(getName() + " arrived the barrier, I am waiting...");
          cyclicBarrier.await();
          Utils.printlnWithTimestamp(getName() + " continues running...");
        } catch (InterruptedException e) {
          Utils.printlnWithTimestamp(getName() + " is interrupted.");
        } catch (BrokenBarrierException e) {
          Utils.printlnWithTimestamp(getName() + " got BrokenBarrierException."); // TODO: ???
        }
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
    WorkThread worker1 = new WorkThread("worker-1", cyclicBarrier);
    WorkThread worker2 = new WorkThread("worker-2", cyclicBarrier);
    WorkThread worker3 = new WorkThread("worker-3", cyclicBarrier);
    worker1.start();
    worker2.start();
    worker3.start();
    Thread.sleep(6000);
    worker1.interrupt();
  }

}
