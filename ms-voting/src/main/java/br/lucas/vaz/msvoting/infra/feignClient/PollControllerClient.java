package br.lucas.vaz.msvoting.infra.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.lucas.vaz.msvoting.domain.Poll;

@FeignClient(value = "ms-poll", path = "/polls")
public interface PollControllerClient {
  @GetMapping("/{id}")
  Poll getById(@PathVariable("id") Long id);

  @GetMapping("/active/{id}")
  Poll getIfActive(@PathVariable("id") Long id);

  @GetMapping("/voted")
  List<Poll> listAllVoted(@RequestParam boolean voted);
}
