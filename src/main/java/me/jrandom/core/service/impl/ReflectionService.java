package me.jrandom.core.service.impl;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.jrandom.core.exception.FieldInitializationException;
import me.jrandom.core.exception.InstanceCreationException;
import me.jrandom.core.service.ReflectionFieldService;
import me.jrandom.core.service.ReflectionInstanceService;
import me.jrandom.core.type.RandomGenerator;
import me.jrandom.core.type.RandomGeneratorFactory;

public class ReflectionService implements ReflectionInstanceService, ReflectionFieldService {

  private RandomGeneratorFactory randomGeneratorFactory = new RandomGeneratorFactory();

  @Override
  public <T> Collection<T> createEmptyInstances(Class<T> clazz, int size) {
    Collection<T> instances = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      instances.add(createEmptyInstancePrivateHelper(clazz));
    }
    return instances;
  }

  @Override
  public <T> T createEmptyInstance(Class<T> clazz) {
    return createEmptyInstancePrivateHelper(clazz);
  }

  @Override
  public <T> void setRandomValueToAllFields(T instance) {
    Class<?> clazz = instance.getClass();
    List<Field> fields = FieldUtils.getAllFieldsList(clazz);
    fields.forEach(field -> setRandomValueToField(instance, field));
  }

  @Override
  public <T> void setRandomValueToNullFields(T instance) {
    Class<?> clazz = instance.getClass();
    List<Field> fields = FieldUtils.getAllFieldsList(clazz);

    fields.forEach(field -> setRandomValueToNullField(instance, field));
  }

  private <T> T createEmptyInstancePrivateHelper(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      throw new InstanceCreationException(String.format("Can't create instance of [%s]", clazz.getName()), ex);
    }
  }

  private <T> void setRandomValueToNullField(T instance, Field field) {
    try {
      Object fieldValue = FieldUtils.readField(field, instance, true);
      if (fieldValue == null) {
        setRandomValueToField(instance, field);
      }
    } catch (IllegalAccessException ex) {
      throw new FieldInitializationException(String.format("Can't set random data to field [%s]", field.getName()), ex);
    }
  }

  @Override
  public  <T> void setRandomValueToField(T instance, Field field) {
    RandomGenerator generator = randomGeneratorFactory.getGenerator(instance.getClass(), field);
    try {
      FieldUtils.writeField(field, instance, generator.generateRandom(), true);
    } catch (IllegalAccessException ex) {
      throw new FieldInitializationException(String.format("Can't set random data to field [%s]", field.getName()), ex);
    }
  }
}
