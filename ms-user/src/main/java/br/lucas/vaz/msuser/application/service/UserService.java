package br.lucas.vaz.msuser.application.service;

import java.util.Optional;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.format.Formatter;
import br.lucas.vaz.msuser.application.exceptions.InvalidCpf;
import br.lucas.vaz.msuser.application.exceptions.InvalidCpfFormat;
import br.lucas.vaz.msuser.domain.User;
import br.lucas.vaz.msuser.infra.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserService {
  private final UserRepository userRepository;
  @Autowired
  private Formatter formatter;

  @Transactional
  public User save(User user) {

    if (!formatter.isFormatted(user.getCpf())) {
      try {
        String cpfFormatted = formatter.format(user.getCpf());
        user.setCpf(cpfFormatted);
      } catch (Exception e) {
        throw new InvalidCpfFormat();
      }
    }
    if (!validator(user.getCpf())) {
      throw new InvalidCpf();
    }

    Optional<User> userOptional = userRepository.findById(user.getCpf());

    if (userOptional.isPresent()) {
      throw new RuntimeException("User already registered");
    }

    User userCreated = user;
    return userRepository.save(userCreated);
  }

  public Optional<User> get(String cpf) {
    String cpfFormatted = formatter.format(cpf);

    if (!validator(cpfFormatted)) {
      throw new InvalidCpf();
    }
    return userRepository.getByCpf(cpfFormatted);
  }

  public boolean validator(String cpf) {
    CPFValidator validator = new CPFValidator();
    validator.initialize(null);
    return validator.isValid(cpf, null);

  }

}