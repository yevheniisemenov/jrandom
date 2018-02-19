package me.jrandom.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.jrandom.core.builder.CollectionBuilder;
import me.jrandom.core.builder.InstanceBuilder;
import me.jrandom.core.configuration.DataGeneratorConfiguration;

public class JRandom {
  private ReflectionInstanceGenerator reflectionInstanceGenerator = new ReflectionInstanceGenerator();
  private DataGeneratorConfiguration configuration = new DataGeneratorConfiguration();

  private JRandom() {
  }

  public <T> InstanceBuilder<T> getOne(Class<T> clazz) {
    T instance = reflectionInstanceGenerator.generateInstance(clazz);
    return new InstanceBuilder<>(instance);
  }

  public <T> CollectionBuilder<T, List<T>> getList(Class<T> clazz, int size) {
    Collection<T> collection = reflectionInstanceGenerator.generateInstances(clazz, size);
    return new CollectionBuilder<>(new ArrayList<>(collection));
  }

  public <T> CollectionBuilder<T, Set<T>> getSet(Class<T> clazz, int size) {
    Collection<T> collection = reflectionInstanceGenerator.generateInstances(clazz, size);
    return new CollectionBuilder<>(new HashSet<>(collection));
  }

  public <T> CollectionBuilder<T, Set<T>> getCollection(Class<T> clazz, Collection<T> collection, int size) {
    collection.addAll(reflectionInstanceGenerator.generateInstances(clazz, size));
    return new CollectionBuilder<>(new HashSet<>(collection));
  }

  public static JRandomBuilder builder() {
    return new JRandomBuilder();
  }

  public static final class JRandomBuilder {
    private DataGeneratorConfiguration configuration = new DataGeneratorConfiguration();

    JRandomBuilder() {
    }

    public JRandomBuilder withConfiguration(DataGeneratorConfiguration configuration) {
      this.configuration = configuration;
      return this;
    }

    public JRandom build() {
      JRandom jRandom = new JRandom();
      jRandom.configuration = this.configuration;
      return jRandom;
    }
  }
}
