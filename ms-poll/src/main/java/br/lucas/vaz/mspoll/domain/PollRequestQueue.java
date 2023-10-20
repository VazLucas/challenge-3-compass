package br.lucas.vaz.mspoll.domain;

import lombok.Data;

@Data

public class PollRequestQueue {
  private Long Id;

  private String name;

  private Boolean voted;

  private Integer result;
}
