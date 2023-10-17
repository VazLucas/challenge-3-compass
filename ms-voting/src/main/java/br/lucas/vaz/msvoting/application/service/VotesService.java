package br.lucas.vaz.msvoting.application.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.lucas.vaz.msvoting.domain.Poll;
import br.lucas.vaz.msvoting.domain.QueryBuilder;
import br.lucas.vaz.msvoting.domain.SingleVote;
import br.lucas.vaz.msvoting.domain.User;
import br.lucas.vaz.msvoting.domain.Votes;
import br.lucas.vaz.msvoting.infra.feignClient.PollControllerClient;
import br.lucas.vaz.msvoting.infra.feignClient.UserControllerClient;
import br.lucas.vaz.msvoting.infra.repository.VotesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotesService {

  private final VotesRepository voteRepository;
  private final UserControllerClient userControllerClient;
  private final PollControllerClient pollControllerClient;

  @Transactional
  public Votes save(String cpf, Long pollId, SingleVote singleVote) {
    User user = userControllerClient.getByCpf(cpf);
    Poll poll = pollControllerClient.getById(pollId);
    Votes vote = new Votes();
    vote.setPollId(poll.getId());
    vote.setUserId(user.getCpf());
    vote.setVote(singleVote);
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
