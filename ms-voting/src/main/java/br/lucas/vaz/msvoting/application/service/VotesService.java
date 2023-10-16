package br.lucas.vaz.mspoll.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.lucas.vaz.mspoll.domain.Votes;
import br.lucas.vaz.mspoll.infra.repository.VotesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotesService {

  private final VotesRepository voteRepository;

  @Transactional
  public Votes save(Votes vote) {
    return voteRepository.save(vote);
  }

  public Optional<Votes> getById(Long id) {
    return voteRepository.findById(id);
  }

  public List<Votes> getAll() {
    return voteRepository.findAll();
  }

}
