package br.lucas.vaz.mspoll.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "polls")
@NoArgsConstructor

public class Poll {

  public Poll(LocalTime localTime) {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @NotEmpty
  @Column(nullable = false)
  private String name;

  private Integer minutesActive;

  private LocalTime endTime;

  private LocalTime createdTime;

  private Boolean active;
  private Boolean voted;

}
