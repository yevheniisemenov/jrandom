package me.jrandom.core.type.impl;

import me.jrandom.core.type.RandomGenerator;

public class IntegerRandomGenerator implements RandomGenerator<Integer> {

  @Override
  public Integer generateRandom() {
    return 42;
  }

  @Override
  public Class<Integer> getType() {
    return Integer.class;
  }
}
