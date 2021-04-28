package org.jasmineliuliuliu.learning.kafka.producer;

import java.util.Map;
import org.jasmineliuliuliu.learning.kafka.common.MessagesSource;
import org.jasmineliuliuliu.learning.kafka.common.MessagesSource.QAndA;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MessagesSource.class)
public class ProducerMessagesConfig {

  @Bean
  public Map<Integer, QAndA> messages(MessagesSource messagesSource) {
    return messagesSource.getMessages();
  }
}
