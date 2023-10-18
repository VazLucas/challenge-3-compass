package br.lucas.vaz.mspoll.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
  private QueryBuilder() {
  }

  public static Example<Poll> makeQuery(Poll poll) {
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
    return Example.of(poll, exampleMatcher);
  }
}
