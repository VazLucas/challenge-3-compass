package br.lucas.vaz.msvoting.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
  private QueryBuilder() {
  }

  public static Example<Votes> makeQuery(Votes vote) {
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
    return Example.of(vote, exampleMatcher);
  }
}
