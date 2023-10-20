package br.lucas.vaz.msvoting.application.config;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.lucas.vaz.msvoting.application.service.VotesService;
import br.lucas.vaz.msvoting.domain.SingleVote;
import br.lucas.vaz.msvoting.infra.feignClient.PollControllerClient;
import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledConfig {

  private final PollControllerClient pollControllerClient;
  private final VotesService votesService;

  @Scheduled(fixedRate = 30000)
  public void checkIfVoted() {
    pollControllerClient.listAllVoted(true).forEach(poll -> {

      if (poll.getResult() == null) {

        Integer yesVotes = votesService.totalVotes(SingleVote.YES, poll.getId());
        Integer noVotes = votesService.totalVotes(SingleVote.NO, poll.getId());
        Integer result = yesVotes - noVotes;
        poll.setResult(result);
        if (result <= 0) {

          votesService.sendResultToUser("The poll " + poll.getName() + " with ID " + poll.getId() +
              " was REFUSED by " + noVotes + " employees");

        } else {
          votesService.sendResultToUser("The poll " + poll.getId() + " with ID " + poll.getId() +
              " was APPROVED by " + yesVotes + " employees");
        }
        try {
          votesService.sendResultToPoll(poll);
        } catch (JsonProcessingException e) {
          e.getMessage();
        }

      }
      ;
    });
  }
}
