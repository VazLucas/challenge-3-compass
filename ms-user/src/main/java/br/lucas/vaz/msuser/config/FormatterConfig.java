package br.lucas.vaz.msuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.format.Formatter;

@Configuration
public class FormatterConfig {

  @Bean
  public Formatter formatter() {
    return new CPFFormatter();
  }
}
