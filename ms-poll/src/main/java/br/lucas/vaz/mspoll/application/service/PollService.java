package br.lucas.vaz.mspoll.application.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.lucas.vaz.mspoll.domain.Poll;
import br.lucas.vaz.mspoll.domain.QueryBuilder;
import br.lucas.vaz.mspoll.infra.repository.PollRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PollService {

  private final PollRepository pollRepository;

  @Value("${poll.default.time}")
  private Integer defaultTime;

  @Transactional
  public Poll save(Poll poll) {
    if (poll.getMinutesActive() <= 0) {
      poll.setMinutesActive(defaultTime);
    }
    LocalTime time = LocalTime.now();
    poll.setCreatedTime(time);
    poll.setEndTime(time);
    poll.setActive(false);
    poll.setVoted(false);
    return pollRepository.save(poll);
  }

  @Transactional
  public Poll updateActivePoll(Long id) {
    Optional<Poll> poll = pollRepository.findById(id);
    Poll pollActivated = poll.get();
    LocalTime time = LocalTime.now();
    pollActivated.setEndTime(time.plusMinutes(pollActivated.getMinutesActive()));
    pollActivated.setActive(true);
    return pollRepository.save(pollActivated);
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

  public List<Poll> getAllVoted(boolean voted) {
    Example<Poll> query = QueryBuilder.makeQuery(new Poll(voted));
    List<Poll> query1 = new ArrayList<>();
    pollRepository.findAll(query).forEach(poll -> {
      if (poll.getVoted()) {
        query1.add(poll);
      }
    });
    return query1;
  }
}
