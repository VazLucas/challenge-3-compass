package br.lucas.vaz.mspoll.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.lucas.vaz.mspoll.application.service.PollService;
import br.lucas.vaz.mspoll.domain.Poll;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/polls")
public class PollController {

  @Autowired
  private PollService pollService;

  @PostMapping
  public ResponseEntity<Poll> createPoll(@RequestBody @Valid Poll poll) {
    Poll pollCreated = pollService.save(poll);
    return ResponseEntity.status(HttpStatus.CREATED).body(pollCreated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Poll> getById(@PathVariable("id") Long id) {
    return pollService.getById(id).map(poll -> ResponseEntity.ok(poll))
        .orElseGet(() -> ResponseEntity.notFound().header("Error", "Poll not found for id " + id).build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Poll> setAsActive(@PathVariable("id") Long id) {
    Optional<Poll> optionalPoll = pollService.getById(id);

    if (!optionalPoll.isPresent()) {
      return ResponseEntity.notFound().header("Error", "Poll not found for id " + id).build();
    }

    Poll pollAsActive = optionalPoll.get();

    if (pollAsActive.getVoted()) {
      return ResponseEntity.badRequest()
          .header("Error", "The poll | " + pollAsActive.getName() + " | is closed and has already been voted")
          .build();
    }
    if (pollAsActive.getActive()) {
      return ResponseEntity.noContent()
          .header("Error", "The poll is already active").build();
    }

    pollService.updateActivePoll(pollAsActive.getId());
    return ResponseEntity.ok(pollAsActive);

  }

  @GetMapping("/active/{id}")
  public ResponseEntity<Optional<Poll>> getIfActive(@PathVariable("id") Long id) {
    Optional<Poll> poll = pollService.getById(id);
    Poll pollActive = new Poll();
    if (!poll.isEmpty()) {
      pollActive = poll.get();
    }
    if (!pollActive.getActive()) {
      return ResponseEntity.notFound().header("Error", "Poll not active for id " + id).build();
    }
    return ResponseEntity.ok(poll);
  }

  @GetMapping("/voted")
  public ResponseEntity<List<Poll>> listAllVoted(boolean voted) {
    List<Poll> votedPolls = pollService.getAllVoted(true);
    return ResponseEntity.ok(votedPolls);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Poll>> listAll() {
    List<Poll> polls = pollService.getAll();
    return ResponseEntity.ok(polls);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
    pollService.remove(id);
    return ResponseEntity.noContent().build();
  }

}
