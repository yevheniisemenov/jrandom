package me.jrandom.core.type.impl;

import me.jrandom.core.type.RandomGenerator;

public class StringRandomGenerator implements RandomGenerator<String> {

  @Override
  public String generateRandom() {
    return "blabla";
  }

  @Override
  public Class<String> getType() {
    return String.class;
  }
}
