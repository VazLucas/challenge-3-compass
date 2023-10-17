package br.lucas.vaz.msvoting.infra.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.lucas.vaz.msvoting.domain.User;

@FeignClient(value = "ms-user", path = "/users")
public interface UserControllerClient {

  @GetMapping("/{cpf}")
  User getByCpf(@PathVariable("cpf") String cpf);
}
