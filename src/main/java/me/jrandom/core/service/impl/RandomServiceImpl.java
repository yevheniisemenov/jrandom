package me.jrandom.core.service.impl;

import net.jodah.typetools.TypeResolver;

import me.jrandom.core.builder.markertype.Setter;
import me.jrandom.core.service.RandomService;
import me.jrandom.core.type.RandomGenerator;
import me.jrandom.core.type.RandomGeneratorFactory;

public class RandomServiceImpl implements RandomService {

  private final RandomGeneratorFactory randomGeneratorFactory = new RandomGeneratorFactory();

  @Override
  public <T, U> void setRandomValue(T instance, Setter<T, U> setter) {
    Class<?>[] argumentTypes = TypeResolver.resolveRawArguments(Setter.class, setter.getClass());
    @SuppressWarnings("unchecked")
    Class<U> valueType = (Class<U>) argumentTypes[1];

    RandomGenerator<U> gen = randomGeneratorFactory.getGenerator(valueType);
    setter.accept(instance, gen.generateRandom());
  }
}
