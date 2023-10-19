package br.lucas.vaz.msvoting.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import br.lucas.vaz.msvoting.domain.Votes;

public interface VotesRepository extends JpaRepository<Votes, Long> {

  @Override
  <S extends Votes> List<S> findAll(Example<S> example);

  Optional<Votes> findByUserIdAndPollId(String userId, Long pollId);
}