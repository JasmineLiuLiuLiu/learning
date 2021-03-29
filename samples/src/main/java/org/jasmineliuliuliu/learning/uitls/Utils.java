package org.jasmineliuliuliu.learning.uitls;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {

  public static void printlnWithTimestamp(String info) {
    System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " " + info);
  }
}
