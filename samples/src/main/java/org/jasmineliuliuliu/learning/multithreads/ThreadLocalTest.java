package org.jasmineliuliuliu.learning.multithreads;

import java.nio.charset.StandardCharsets;
import org.jasmineliuliuliu.learning.uitls.Utils;

public class ThreadLocalTest {

  static ThreadLocal<String> localString = new ThreadLocal<>();
  static InheritableThreadLocal<String> inheritableLocalString = new InheritableThreadLocal<>();

  public static void main(String[] args) throws InterruptedException {
    localString.set("AAAAAA");
    inheritableLocalString.set("aaaaa");
    Utils.printlnWithTimestamp("main thread local string is: " + localString.get());
    Utils.printlnWithTimestamp(
        "main inheritable thread local string is: " + inheritableLocalString.get());
    Thread.sleep(1000);
    new Thread(new Runnable() {
      @Override
      public void run() {
        Utils.printlnWithTimestamp("child thread local string (init) is: " + localString.get());
        Utils.printlnWithTimestamp(
            "child inheritable thread local string (init) is: " + inheritableLocalString.get());
        localString.set("BBBBB");
        inheritableLocalString.set("bbbbb");
        Utils.printlnWithTimestamp("child thread local string (set)  is: " + localString.get());
        Utils.printlnWithTimestamp(
            "child inheritable thread local string (set) is: " + inheritableLocalString.get());
      }
    }).start();
    Thread.sleep(1000);
    Utils.printlnWithTimestamp("main thread local string is: " + localString.get());
    Utils.printlnWithTimestamp(
        "main inheritable thread local string is: " + inheritableLocalString.get());
  }

}
