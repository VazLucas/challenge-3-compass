package br.lucas.vaz.msvoting.application.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.lucas.vaz.msvoting.domain.QueryBuilder;
import br.lucas.vaz.msvoting.domain.Votes;
import br.lucas.vaz.msvoting.infra.repository.VotesRepository;
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

  public List<Votes> getAll() {
    return voteRepository.findAll();
  }

  public List<Votes> listByPoll(Long pollId) {
    Example<Votes> query = QueryBuilder.makeQuery(new Votes(pollId));
    return voteRepository.findAll(query);
  }
}
