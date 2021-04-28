package org.jasmineliuliuliu.learning.kafka.producer;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jasmineliuliuliu.learning.kafka.common.MessagesSource.QAndA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProduceService {

  @Autowired
  Map<Integer, QAndA> messages;

  @Bean
  public Supplier<QAndA> ask() {
//    return () -> Flux.fromStream(
//        Stream.generate(() -> messages.get(ThreadLocalRandom.current().nextInt(messages.size()))));
    return ()-> messages.get(ThreadLocalRandom.current().nextInt(messages.size()));
  }


}
