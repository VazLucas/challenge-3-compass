package br.lucas.vaz.mspoll.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.lucas.vaz.mspoll.domain.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {

}
