package org.jasmineliuliuliu.learning.multithreads;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import org.jasmineliuliuliu.learning.uitls.Utils;

public class CountDownLatchTest {

  static class CountDownThread extends Thread {

    private final CountDownLatch countDownLatch;

    CountDownThread(String name, CountDownLatch countDownLatch) {
      super(name);
      this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
      Utils.printlnWithTimestamp(currentThread().getName() + " is running...");
      try {
        Thread.sleep(3000);
        Utils.printlnWithTimestamp(currentThread().getName() + " is finished.");
        countDownLatch.countDown();
      } catch (InterruptedException e) {
      }
    }
  }

  static class WaitThread extends Thread {

    private final CountDownLatch countDownLatch;

    WaitThread(String name, CountDownLatch countDownLatch) {
      super(name);
      this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
      Utils.printlnWithTimestamp(currentThread().getName() + " begins waiting...");
      try {
        countDownLatch.await();
      } catch (InterruptedException e) {
      }
      Utils.printlnWithTimestamp(Thread.currentThread().getName()
          + " checks CountDownLatch's count, it is: " + countDownLatch.getCount());
      Utils.printlnWithTimestamp(currentThread().getName() + " continues running...");
    }
  }

  public static void main(String[] args) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(2);

    Utils.printlnWithTimestamp(Thread.currentThread().getName()
        + " checks CountDownLatch's count, it is: " + countDownLatch.getCount());

    new CountDownThread("countDown-1", countDownLatch).start();
    new CountDownThread("countDown-2", countDownLatch).start();

    new WaitThread("wait-1", countDownLatch).start();
    new WaitThread("wait-2", countDownLatch).start();

    Thread.sleep(1000);

    Utils.printlnWithTimestamp(Thread.currentThread().getName() + " begins waiting...");
    countDownLatch.await();

    Utils.printlnWithTimestamp(Thread.currentThread().getName()
        + " checks CountDownLatch's count, it is: " + countDownLatch.getCount());
    Utils.printlnWithTimestamp(Thread.currentThread().getName() + " continues running ...");
  }

}
