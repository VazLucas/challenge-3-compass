package br.lucas.vaz.msuser.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.lucas.vaz.msuser.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> getByCpf(String cpf);

}
