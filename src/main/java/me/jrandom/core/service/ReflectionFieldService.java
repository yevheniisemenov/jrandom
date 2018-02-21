package me.jrandom.core.service;

public interface ReflectionFieldService {

  <T> void setRandomValueToAllFields(T instance);

  <T> void setRandomValueToNullFields(T instance);
}
