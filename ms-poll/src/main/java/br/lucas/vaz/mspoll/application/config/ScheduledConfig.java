package br.lucas.vaz.mspoll.application.config;

import java.time.LocalTime;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.lucas.vaz.mspoll.infra.repository.PollRepository;

@EnableScheduling
public class ScheduledConfig {

  private final PollRepository pollRepository;

  public ScheduledConfig(PollRepository pollRepository) {
    this.pollRepository = pollRepository;
  }

  @Scheduled(fixedRate = 2000)
  public void checkVoted() {
    pollRepository.findAll().forEach(poll -> {
      if (!poll.getActive() && !poll.getCreatedTime().equals(poll.getEndTime())) {
        poll.setVoted(true);
        pollRepository.save(poll);
      }
    });
  };

  @Scheduled(fixedRate = 2000)
  public void checkActive() {
    LocalTime now = LocalTime.now();
    pollRepository.findAll().forEach(poll -> {
      if (poll.getEndTime().isBefore(now)) {
        poll.setActive(false);
        pollRepository.save(poll);
      }

    });
  };

}