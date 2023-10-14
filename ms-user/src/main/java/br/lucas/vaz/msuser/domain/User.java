package br.lucas.vaz.msuser.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @NotEmpty
  @Column(nullable = false, unique = true, name = "Id")
  private String cpf;

  @NotEmpty
  @Column(nullable = false)
  private String name;

}
