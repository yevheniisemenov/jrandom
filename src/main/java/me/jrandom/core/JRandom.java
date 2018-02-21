package me.jrandom.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.jrandom.core.builder.CollectionBuilder;
import me.jrandom.core.builder.InstanceBuilder;
import me.jrandom.core.configuration.Configuration;
import me.jrandom.core.configuration.DataGeneratorConfiguration;
import me.jrandom.core.service.ReflectionInstanceService;
import me.jrandom.core.service.impl.ReflectionService;

public class JRandom {
  private ReflectionInstanceService reflectionInstanceService = new ReflectionService();

  private JRandom() {
  }

  public <T> InstanceBuilder<T> getOne(Class<T> clazz) {
    T newInstance = reflectionInstanceService.createEmptyInstance(clazz);
    return new InstanceBuilder<>(newInstance);
  }

  public <T> CollectionBuilder<T, List<T>> getList(Class<T> clazz, int size) {
    Collection<T> collection = reflectionInstanceService.createEmptyInstances(clazz, size);
    return new CollectionBuilder<>(new ArrayList<>(collection));
  }

  public <T> CollectionBuilder<T, Set<T>> getSet(Class<T> clazz, int size) {
    Collection<T> collection = reflectionInstanceService.createEmptyInstances(clazz, size);
    return new CollectionBuilder<>(new HashSet<>(collection));
  }

  public <T> CollectionBuilder<T, Collection<T>> getCollection(Class<T> clazz, Collection<T> collection, int size) {
    collection.addAll(reflectionInstanceService.createEmptyInstances(clazz, size));
    return new CollectionBuilder<>(collection);
  }

  public static JRandomBuilder builder() {
    return new JRandomBuilder();
  }

  public static final class JRandomBuilder {
    private DataGeneratorConfiguration configuration = new DataGeneratorConfiguration();

    private JRandomBuilder() {
    }

    public JRandomBuilder withConfiguration(DataGeneratorConfiguration configuration) {
      this.configuration = configuration;
      return this;
    }

    public JRandom build() {
      JRandom jRandom = new JRandom();
      Configuration.INSTANCE.set(configuration);
      return jRandom;
    }
  }
}
