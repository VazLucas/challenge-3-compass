package br.lucas.vaz.msuser.application.exceptions;

public class InvalidCpf extends IllegalArgumentException {
  public InvalidCpf() {
    super(
        "Invalid CPF, the validation proccess returned it as an invalid combination");
  }
}
