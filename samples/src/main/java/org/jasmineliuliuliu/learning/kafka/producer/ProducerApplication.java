package org.jasmineliuliuliu.learning.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.jasmineliuliuliu.learning.kafka.common",
    "org.jasmineliuliuliu.learning.kafka.producer"})
public class ProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class, args);
  }
}
