package br.lucas.vaz.mspoll.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.lucas.vaz.mspoll.application.service.VotesService;
import br.lucas.vaz.mspoll.domain.Votes;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/votes")
public class VotesController {

  @Autowired
  private VotesService votesService;

  @PostMapping
  public ResponseEntity<Votes> createVote(@RequestBody @Valid Votes votes) {
    Votes pollCreated = pollService.save(poll);
    return ResponseEntity.status(HttpStatus.CREATED).body(pollCreated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Votes> getById(@PathVariable("id") Long id) {
    return pollService.getById(id).map(poll -> ResponseEntity.ok(poll))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/all")
  public ResponseEntity<List<Votes>> listAll() {
    List<Votes> polls = pollService.getAll();
    return ResponseEntity.ok(polls);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
    pollService.remove(id);
    return ResponseEntity.noContent().build();
  }

}
