package me.jrandom.core.type;

import me.jrandom.core.type.impl.StringRandomGenerator;

public enum Type {
  NAME(new StringRandomGenerator()),
  SURNAME(new StringRandomGenerator()),
  COUNTRY(new StringRandomGenerator()),
  CITY(new StringRandomGenerator());

  private RandomGenerator randomGenerator;

  Type(RandomGenerator randomGenerator) {
    this.randomGenerator = randomGenerator;
  }

  public RandomGenerator getRandomGenerator() {
    return randomGenerator;
  }
}
