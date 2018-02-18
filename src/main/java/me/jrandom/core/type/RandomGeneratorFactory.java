package me.jrandom.core.type;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import me.jrandom.core.configuration.Mapper;
import me.jrandom.core.type.impl.StringRandomGenerator;

public class RandomGeneratorFactory {

  private static final RandomGenerator<String> stringRandomGenerator = new StringRandomGenerator();

  private Set<RandomGenerator> randomGenerators = new HashSet<>(Arrays.asList(stringRandomGenerator));

  public RandomGenerator getGenerator(Class<?> type) {
    Optional<RandomGenerator> compatibleGenerator = randomGenerators.stream()
        .filter(generator -> generator.getType().equals(type))
        .findFirst();

    return compatibleGenerator.orElseThrow(() -> new RuntimeException("Generator not found"));
  }

  public RandomGenerator getGenerator(Class<?> type, Field field, Mapper mapper) {
    return mapper.getGeneratorMapping().entrySet().stream()
        .filter(entry -> entry.getKey().equals(field.getName()))
        .map(Map.Entry::getValue)
        .findFirst()
        .orElseGet(() -> getGenerator(type));
  }
}
