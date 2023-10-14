package br.lucas.vaz.msuser.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.lucas.vaz.msuser.domain.User;
import br.lucas.vaz.msuser.infra.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  public Optional<User> get(String cpf) {
    return userRepository.getByCpf(cpf);
  }

  public Object getByCpf(String cpfFormatted) {
    return null;
  }

}
