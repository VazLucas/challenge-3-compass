package br.lucas.vaz.msuser.application.exceptions;

public class InvalidCpf extends IllegalArgumentException {
  public InvalidCpf() {
    super(
        "Invalid CPF, please check if there is a typo or if it has no symbols as hyphen or dots. Try with this template XXXXXXXXX");
  }
}
