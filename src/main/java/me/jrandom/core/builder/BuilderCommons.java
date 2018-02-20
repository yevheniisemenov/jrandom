package me.jrandom.core.builder;

import static me.jrandom.core.ReflectionAction.accessibleSafeAction;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

import me.jrandom.core.ReflectionInstanceGenerator;
import me.jrandom.core.configuration.Conf;
import me.jrandom.core.configuration.Mapper;
import me.jrandom.core.exception.FieldInitializationException;

public class BuilderCommons {

  private ReflectionInstanceGenerator reflectionInstanceGenerator = new ReflectionInstanceGenerator();

  public <T> void setRandomDataToFields(T instance) {
    Class<?> clazz = instance.getClass();
    List<Field> fields = FieldUtils.getAllFieldsList(clazz);

    fields.forEach(field -> setRandomDataToField(instance, field));
  }

  private <T> void setRandomDataToField(T instance, Field field) {
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
