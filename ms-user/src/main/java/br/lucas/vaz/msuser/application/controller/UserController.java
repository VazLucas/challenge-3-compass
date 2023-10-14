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
import br.lucas.vaz.msuser.application.service.UserService;
import br.lucas.vaz.msuser.domain.User;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private Formatter formatter;

  @GetMapping
  public String status() {
    return "ok";
  }

  @PostMapping("/new")
  public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
    String cpfFormatted = formatter.format(user.getCpf());
    user.setCpf(cpfFormatted);
    User userCreated = userService.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<User> getByCpf(@PathVariable("cpf") String cpf) {
    String cpfFormatted = formatter.format(cpf);
    return userService.get(cpfFormatted).map(user -> ResponseEntity.ok(user))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
