package br.lucas.vaz.msvoting.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.lucas.vaz.msvoting.domain.Poll;
import br.lucas.vaz.msvoting.domain.QueryBuilder;
import br.lucas.vaz.msvoting.domain.SingleVote;
import br.lucas.vaz.msvoting.domain.User;
import br.lucas.vaz.msvoting.domain.Votes;
import br.lucas.vaz.msvoting.infra.feignClient.PollControllerClient;
import br.lucas.vaz.msvoting.infra.feignClient.UserControllerClient;
import br.lucas.vaz.msvoting.infra.queues.JsonResultPublisher;
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
  private final JsonResultPublisher jsonResultPublisher;

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
    List<Votes> listByPoll = voteRepository.findAll(query);
    if (listByPoll.isEmpty()) {
      throw new RuntimeException("No votes for this poll " + pollId);
    }
    return listByPoll;
  }

  public void sendResultToPoll(Poll poll) throws JsonProcessingException {
    jsonResultPublisher.sendResultToPoll(poll);
  }

  public Integer totalVotes(SingleVote vote, Long pollId) {
    Optional<List<Votes>> totalVotesOptional = voteRepository.findAllByVoteAndPollId(vote, pollId);
    List<Votes> totalVotes = totalVotesOptional.get();
    return totalVotes.size();

  }

  public void sendResultToUser(String message) {
    jsonResultPublisher.sendResultToUsers(message);
  }
}