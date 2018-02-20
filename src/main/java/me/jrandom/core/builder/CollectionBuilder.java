package me.jrandom.core.builder;


import static me.jrandom.core.ReflectionAction.accessibleSafeAction;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import me.jrandom.core.ReflectionInstanceGenerator;
import me.jrandom.core.configuration.Conf;
import me.jrandom.core.configuration.Mapper;
import me.jrandom.core.exception.FieldInitializationException;

public class CollectionBuilder<T, V extends Collection<T>> {

  private V collection;
  private ReflectionInstanceGenerator reflectionInstanceGenerator = new ReflectionInstanceGenerator();

  public CollectionBuilder(V collection) {
    this.collection = collection;
  }

  public <S> CollectionBuilder<T, V> set(Setter<T, S> setter, CollectionBuildingStrategy<Collection<T>, Setter<T, S>> strategy) {
    strategy.accept(collection, setter);
    return this;
  }

  public V build() {

    // fill here fields with null values with random data


    // todo refactor duplicate
    for (T instance : collection) {
      List<Field> allFieldsList = FieldUtils.getAllFieldsList(instance.getClass());
      for (Field field : allFieldsList) {
        accessibleSafeAction(field, () -> {
          try {
            if (field.get(instance) == null) {
              Mapper fieldTypeMapper = Conf.INSTANCE.getConfiguration().getMapper(field.getType());
              reflectionInstanceGenerator.setRandomValueForField(instance, fieldTypeMapper, field);
            }
          } catch (IllegalAccessException e) {
            throw new FieldInitializationException("adqweda", e);
          }
        });
      }
    }


    return collection;
  }
}
