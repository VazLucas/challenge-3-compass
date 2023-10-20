package br.lucas.vaz.msuser.infra.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsonResultConsumer {

  @RabbitListener(queues = "${rabbitmq.queue.result-publisher}")
  public void resultConsumer(String message) {
    System.out.println(message);
  }
}
