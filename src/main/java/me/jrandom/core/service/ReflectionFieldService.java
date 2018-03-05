package me.jrandom.core.service;

import java.lang.reflect.Field;

public interface ReflectionFieldService {

  <T> void setRandomValueToAllFields(T instance);

  <T> void setRandomValueToNullFields(T instance);

  <T> void setRandomValueToField(T instance, Field field);
}
