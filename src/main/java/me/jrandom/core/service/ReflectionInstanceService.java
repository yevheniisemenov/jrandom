package me.jrandom.core.service;

import java.util.Collection;

public interface ReflectionInstanceService {

  <T> T createEmptyInstance(Class<T> clazz);

  <T> Collection<T> createEmptyInstances(Class<T> clazz, int size);
}
