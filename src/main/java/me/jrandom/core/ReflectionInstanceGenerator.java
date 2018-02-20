package me.jrandom.core;

import static me.jrandom.core.ReflectionAction.accessibleSafeAction;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
      T newInstance = createNewInstance(clazz);
      instances.add(fillInstanceByRandomData(newInstance));
    }
    return instances;
  }

  public <T> Collection<T> generateEmptyInstances(Class<T> clazz, int size) {
    Collection<T> instances = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      instances.add(createNewInstance(clazz));
    }
    return instances;
  }

  public <T> T fillInstanceByRandomData(T instance) {
    List<Field> allFieldsList = FieldUtils.getAllFieldsList(instance.getClass());
    Mapper mapper = configuration.getMapper(instance.getClass());

    for (Field field : allFieldsList) {
      setRandomValueForField(instance, mapper, field);
    }
    return instance;
  }

  public <T> void setRandomValueForField(T instance, Mapper mapper, Field field) {
    RandomGenerator generator = randomGeneratorFactory.getGenerator(instance.getClass(), field, mapper);
    setRandomValueForField(instance, field, generator);
  }

  private <T> void setRandomValueForField(T instance, Field field, RandomGenerator generator) {
    accessibleSafeAction(field, () -> {
      try {
        field.set(instance, generator.generateRandom());
      } catch (IllegalAccessException ex) {
        throw new FieldInitializationException(String.format("Can't set random data to field [%s]", field.getName()), ex);
      }
    });
  }

  public <T> T createNewInstance(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      throw new InstanceCreationException(String.format("Can't create instance of [%s]", clazz.getName()), ex);
    }
  }
}
