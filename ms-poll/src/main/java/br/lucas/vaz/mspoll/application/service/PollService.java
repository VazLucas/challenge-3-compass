package br.lucas.vaz.mspoll.application.service;

import java.util.List;
import java.util.Optional;

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
}
