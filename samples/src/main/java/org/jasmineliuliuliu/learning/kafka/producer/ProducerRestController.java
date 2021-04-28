package org.jasmineliuliuliu.learning.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ProducerRestController {

  @Autowired
  ProduceService produceService;

  @GetMapping("/ask")
  public String produce() {
    return "Success";
  }
}
