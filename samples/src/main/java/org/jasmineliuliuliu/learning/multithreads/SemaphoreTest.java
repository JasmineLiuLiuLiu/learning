package org.jasmineliuliuliu.learning.multithreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.jasmineliuliuliu.learning.uitls.Utils;

/**
 * <p>简单的Semaphore使用测试</p>
 * <p>例子中使用了Semaphore，初始值为5</p>
 * <p>每个Runnable Task都请求Semaphore一个许可，打印一行log，2s之后释放许可</p>
 * <p>在main函数中使用一个线程数为10的线程池，提交10个这样的Runnable task</p>
 * <p>结果发现，开始只有5个task被运行了，2s之后，又运行5个task</p>
 */
public class SemaphoreTest {

  public static class MyThread implements Runnable {

    private final int id;
    private final Semaphore semaphore;

    public MyThread(int id, Semaphore semaphore) {
      this.id = id;
      this.semaphore = semaphore;
    }

    @Override
    public void run() {
      try {
        semaphore.acquire();
        Utils.printlnWithTimestamp(Thread.currentThread().getName() + " is running task-" + id);
        Thread.sleep(2000);
        semaphore.release();
      } catch (InterruptedException e) {
      }
    }
  }

  public static void main(String[] args) {
    Semaphore semaphore = new Semaphore(5);
    ExecutorService es = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 10; i++) {
      es.submit(new MyThread(i, semaphore));
    }
    es.shutdown();
  }

}
