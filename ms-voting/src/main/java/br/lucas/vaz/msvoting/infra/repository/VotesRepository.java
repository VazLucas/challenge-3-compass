package br.lucas.vaz.mspoll.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.lucas.vaz.mspoll.domain.Votes;

public interface VotesRepository extends JpaRepository<Votes, Long>, QueryByExampleExecutor<Votes> {
  @Override
  <S extends Planet> List<S> findAll(Example<S> example);
}
