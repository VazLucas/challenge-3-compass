package br.lucas.vaz.mspoll.infra.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.lucas.vaz.mspoll.domain.Poll;
import br.lucas.vaz.mspoll.domain.PollRequestQueue;
import br.lucas.vaz.mspoll.infra.repository.PollRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsonResultConsumer {

  private final PollRepository pollRepository;

  @RabbitListener(queues = "${rabbitmq.queue.result-publisher}")
  public void resultConsumer(@Payload String payload) {
    try {
      var mapper = new ObjectMapper();

      PollRequestQueue pollRequestQueue = mapper.readValue(payload, PollRequestQueue.class);

      Poll poll = pollRepository.findById(pollRequestQueue.getId()).orElseThrow();

      poll.setResult(pollRequestQueue.getResult());

      pollRepository.save(poll);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
