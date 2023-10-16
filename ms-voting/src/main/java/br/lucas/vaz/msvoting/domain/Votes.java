package br.lucas.vaz.msvoting.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

public class Votes implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long Id;

  private Long pollId;

  @Enumerated(EnumType.STRING)
  private SingleVote vote;

  public Votes(Long pollId) {
    this.pollId = pollId;
  }
}
