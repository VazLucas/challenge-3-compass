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
@Table(name = "tb_user")
public class User {

  @Id
  @NotEmpty
  @Column(nullable = false, unique = true, name = "cpf")
  private String cpf;

  @NotEmpty
  @Column(nullable = false, name = "userName")
  private String name;

}