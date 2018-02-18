package me.jrandom.core;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

import me.jrandom.core.configuration.DataGeneratorConfiguration;
import me.jrandom.core.configuration.Mapper;
import me.jrandom.core.type.RandomGenerator;
import me.jrandom.core.type.RandomGeneratorFactory;


public class DataGenerator {

  private RandomGeneratorFactory randomGeneratorFactory = new RandomGeneratorFactory();
  private DataGeneratorConfiguration configuration = new DataGeneratorConfiguration();

  private DataGenerator() {
  }

  public <T> T get(Class<T> clazz) {

    List<Field> allFieldsList = FieldUtils.getAllFieldsList(clazz);

    T instance = createNewInstance(clazz);

    Mapper mapper = configuration.getMapper(clazz);

    allFieldsList.forEach(field -> {
      RandomGenerator generator = randomGeneratorFactory.getGenerator(clazz, field, mapper);
      setRandomValue(instance, field, generator);
    });
    return instance;
  }


  private <T> void setRandomValue(T instance, Field field, RandomGenerator generator) {
    try {

      boolean isFieldAccessible = field.isAccessible();
      if(!isFieldAccessible) {
        field.setAccessible(true);
      }

      field.set(instance, generator.generateRandom());

      if(!isFieldAccessible) {
        field.setAccessible(false);
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private <T> T createNewInstance(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException();
    }
  }


  public static DataGeneratorBuilder builder() {
    return new DataGeneratorBuilder();
  }

  public static final class DataGeneratorBuilder {
    private DataGeneratorConfiguration configuration;

    DataGeneratorBuilder() {
    }

    public DataGeneratorBuilder withConfiguration(DataGeneratorConfiguration configuration) {
      this.configuration = configuration;
      return this;
    }

    public DataGenerator build() {

      DataGenerator dataGenerator = new DataGenerator();
      dataGenerator.configuration = this.configuration;
      return dataGenerator;
    }
  }
}
