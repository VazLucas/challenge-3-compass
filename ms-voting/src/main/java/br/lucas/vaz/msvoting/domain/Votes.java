package br.lucas.vaz.mspoll.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "votes")
@NoArgsConstructor

public class Votes {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private Long Id;

  @NotEmpty
  private Long pollId;

  @Enumerated(EnumType.STRING)
  private SingleVote vote;

}
