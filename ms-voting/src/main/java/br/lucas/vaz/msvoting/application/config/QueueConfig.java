package br.lucas.vaz.msvoting.application.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
  @Value("${rabbitmq.queue.result-publisher}")
  private String resultPublisher;

  @Value("${rabbitmq.queue.result-publisher-string}")
  private String resultPublisherString;

  @Bean
  public Queue queueResultPoll() {
    return new Queue(resultPublisher, true);
  }

  @Bean
  public Queue queueResultUser() {
    return new Queue(resultPublisherString, true);
  }
}
