package br.lucas.vaz.msvoting.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.lucas.vaz.msvoting.domain.SingleVoteRequest;
import br.lucas.vaz.msvoting.domain.Votes;
import br.lucas.vaz.msvoting.application.service.VotesService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/votes")
public class VotesController {

  @Autowired
  private VotesService votesService;

  @PostMapping
  public ResponseEntity<Votes> createVote(@RequestParam(required = true) String cpf,
      @RequestParam(required = true) Long pollId,
      @RequestBody @Valid SingleVoteRequest vote) {
    Votes voteCreated = votesService.save(cpf, pollId, vote.getVote());
    return ResponseEntity.status(HttpStatus.CREATED).body(voteCreated);
  }

  @GetMapping("/listByPoll")
  public ResponseEntity<List<Votes>> listAll(@RequestParam(required = true) Long pollId) {
    List<Votes> votes = votesService.listByPoll(pollId);
    return ResponseEntity.ok(votes);
  }

}
