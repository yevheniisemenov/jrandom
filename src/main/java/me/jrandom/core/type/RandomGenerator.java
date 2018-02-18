package me.jrandom.core.type;

public interface RandomGenerator<T> {

  T generateRandom();

  Class<T> getType();
}
