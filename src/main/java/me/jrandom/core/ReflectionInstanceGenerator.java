package me.jrandom.core;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.jrandom.core.builder.CollectionBuilder;
import me.jrandom.core.builder.InstanceBuilder;
import me.jrandom.core.configuration.DataGeneratorConfiguration;
import me.jrandom.core.configuration.Mapper;
import me.jrandom.core.exception.FieldInitializationException;
import me.jrandom.core.exception.InstanceCreationException;
import me.jrandom.core.type.RandomGenerator;
import me.jrandom.core.type.RandomGeneratorFactory;

public class ReflectionInstanceGenerator {

  private RandomGeneratorFactory randomGeneratorFactory = new RandomGeneratorFactory();
  private DataGeneratorConfiguration configuration = new DataGeneratorConfiguration();


  public <T> Collection<T> generateInstances(Class<T> clazz, int size) {
    Collection<T> instances = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      instances.add(generateInstance(clazz));
    }
    return instances;
  }

  public <T> T generateInstance(Class<T> clazz) {
    List<Field> allFieldsList = FieldUtils.getAllFieldsList(clazz);
    T instance = createNewInstance(clazz);
    Mapper mapper = configuration.getMapper(clazz);

    for (Field field : allFieldsList) {
      RandomGenerator generator = randomGeneratorFactory.getGenerator(clazz, field, mapper);
      setRandomValue(instance, field, generator);
    }
    return instance;
  }

  private <T> void setRandomValue(T instance, Field field, RandomGenerator generator) {
    try {

      boolean isFieldAccessible = field.isAccessible();
      if (!isFieldAccessible) {
        field.setAccessible(true);
      }

      field.set(instance, generator.generateRandom());

      if (!isFieldAccessible) {
        field.setAccessible(false);
      }
    } catch (IllegalAccessException ex) {
      throw new FieldInitializationException(String.format("Can't set random data to field [%s]", field.getName()), ex);
    }
  }

  private <T> T createNewInstance(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      throw new InstanceCreationException(String.format("Can't create instance of [%s]", clazz.getName()), ex);
    }
  }
}
