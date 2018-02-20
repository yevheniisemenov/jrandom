package me.jrandom.core.builder;

import static me.jrandom.core.ReflectionAction.accessibleSafeAction;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;

import me.jrandom.core.ReflectionInstanceGenerator;
import me.jrandom.core.configuration.Conf;
import me.jrandom.core.configuration.Mapper;
import me.jrandom.core.exception.FieldInitializationException;

public class InstanceBuilder<T> {
  private T instance;
  private ReflectionInstanceGenerator reflectionInstanceGenerator = new ReflectionInstanceGenerator();

  public InstanceBuilder(T instance) {
    this.instance = instance;
  }

  public <S> InstanceBuilder<T> set(BiConsumer<T, S> setter, S value) {
    setter.accept(instance, value);
    return this;
  }

  public T build() {
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
    return instance;
  }
}
