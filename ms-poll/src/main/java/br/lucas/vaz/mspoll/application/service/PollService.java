package br.lucas.vaz.mspoll.application.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.lucas.vaz.mspoll.domain.Poll;
import br.lucas.vaz.mspoll.infra.repository.PollRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PollService {

  private final PollRepository pollRepository;

  @Transactional
  public Poll save(Poll poll) {
    return pollRepository.save(poll);
  }

  public Optional<Poll> getById(Long id) {
    return pollRepository.findById(id);
  }

  public List<Poll> getAll() {
    return pollRepository.findAll();
  }

  public void remove(Long id) {
    pollRepository.deleteById(id);
  }

  @Scheduled(fixedRate = 60000)
  public void checkActive() {
    LocalTime now = LocalTime.now();
    pollRepository.findAll().forEach(poll -> {
      if (poll.getEndTime().isBefore(now)) {
        poll.setActive(false);
        pollRepository.save(poll);
      }
    });

  }
}
