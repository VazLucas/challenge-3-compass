package br.lucas.vaz.msuser.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.stella.format.Formatter;
import br.lucas.vaz.msuser.application.exceptions.InvalidCpf;
import br.lucas.vaz.msuser.application.exceptions.InvalidCpfFormat;
import br.lucas.vaz.msuser.application.service.UserService;
import br.lucas.vaz.msuser.domain.User;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private Formatter formatter;

  @PostMapping("/new")
  public ResponseEntity<User> saveUser(@RequestBody @Valid User user) throws Exception {

    if (!formatter.isFormatted(user.getCpf())) {
      try {
        String cpfFormatted = formatter.format(user.getCpf());
        user.setCpf(cpfFormatted);
      } catch (Exception e) {
        throw new InvalidCpfFormat();
      }
    }
    if (!userService.validator(user.getCpf())) {
      throw new InvalidCpf();

    }
    User userCreated = userService.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<User> getByCpf(@PathVariable("cpf") String cpf) throws Exception {
    String cpfFormatted = formatter.format(cpf);

    if (!userService.validator(cpfFormatted)) {
      throw new InvalidCpf();
    }
    return userService.get(cpfFormatted).map(user -> ResponseEntity.ok(user))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
