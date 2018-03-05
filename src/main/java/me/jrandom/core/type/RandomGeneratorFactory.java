package me.jrandom.core.type;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import me.jrandom.core.configuration.Configuration;
import me.jrandom.core.configuration.Mapper;
import me.jrandom.core.type.impl.IntegerRandomGenerator;
import me.jrandom.core.type.impl.StringRandomGenerator;

public class RandomGeneratorFactory {
  private static final RandomGenerator<String> stringRandomGenerator = new StringRandomGenerator();
  private static final RandomGenerator<Integer> integerRandomGenerator = new IntegerRandomGenerator();
  private Set<RandomGenerator<?>> randomGenerators = new HashSet<>(Arrays.asList(stringRandomGenerator, integerRandomGenerator));

  public RandomGenerator getGenerator(Class<?> clazz, Field clazzField) {
    Mapper mapper = Configuration.INSTANCE.get().getMapper(clazz);
    return mapper.getGeneratorMapping().entrySet().stream()
        .filter(entry -> entry.getKey().equals(clazzField.getName()))
        .map(Map.Entry::getValue)
        .findFirst()
        .orElseGet(() -> getGenerator(clazzField.getType()));
  }

  public <T> RandomGenerator<T> getGenerator(Class<T> type) {
    Optional<RandomGenerator<?>> compatibleGenerator = randomGenerators.stream()
        .filter(generator -> generator.getType().equals(type))
        .findFirst();

    @SuppressWarnings("unchecked")
    RandomGenerator<T> generator = (RandomGenerator<T>) compatibleGenerator.orElseThrow(() -> new RuntimeException("Generator not found"));
    return generator;
  }
}
