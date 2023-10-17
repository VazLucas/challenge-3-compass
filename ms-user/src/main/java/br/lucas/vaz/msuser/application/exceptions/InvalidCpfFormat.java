package br.lucas.vaz.msuser.application.exceptions;

public class InvalidCpfFormat extends IllegalArgumentException {
  public InvalidCpfFormat() {
    super(
        "Invalid CPF, please check if it is correctly formatted");
  }
}
