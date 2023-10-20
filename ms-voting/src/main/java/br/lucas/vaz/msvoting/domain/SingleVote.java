package br.lucas.vaz.msvoting.domain;

import jakarta.validation.constraints.NotEmpty;

@NotEmpty
public enum SingleVote {
  YES, NO
}
