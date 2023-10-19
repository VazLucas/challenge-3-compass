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
import feign.FeignException;
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
    if (singleVote == null) {
      throw new RuntimeException("Vote cannot be null, specify YES or NO");
    }
    User user;
    try {
      user = userControllerClient.getByCpf(cpf);
    } catch (FeignException.NotFound e) {
      throw new RuntimeException("User not found");
    }
    Poll poll;
    try {
      poll = pollControllerClient.getIfActive(pollId);
    } catch (FeignException.NotFound e) {
      throw new RuntimeException("Poll not found");
    }

    Votes vote = new Votes();
    vote.setPollId(poll.getId());
    vote.setUserId(user.getCpf());
    vote.setVote(singleVote);

    if (voteRepository.findByUserIdAndPollId(user.getCpf(), poll.getId()).isPresent()) {
      throw new RuntimeException("User already voted for this poll");
    }

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