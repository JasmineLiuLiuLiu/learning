package org.jasmineliuliuliu.learning.kafka.common;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jasmine.customized")
public class MessagesSource {

  private Map<Integer, QAndA> messages;

  @Data
  public static class QAndA {

    private String q;
    private String a;
  }

}
