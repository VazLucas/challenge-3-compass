package br.lucas.vaz.msvoting.infra.queues;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.lucas.vaz.msvoting.domain.Poll;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsonResultPublisher {

  private final RabbitTemplate rabbitTemplate;
  private final Queue queueResultPoll;
  private final Queue queueResultUser;

  private String convertStringIntoJson(Poll poll) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    var json = mapper.writeValueAsString(poll);
    return json;
  }

  public void sendResultToPoll(Poll poll) throws JsonProcessingException {
    var json = convertStringIntoJson(poll);
    rabbitTemplate.convertAndSend(queueResultPoll.getName(), json);
  }

  public void sendResultToUsers(String message) {
    rabbitTemplate.convertAndSend(queueResultUser.getName(), message);
  }

}
