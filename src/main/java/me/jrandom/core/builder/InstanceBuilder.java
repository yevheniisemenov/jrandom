package me.jrandom.core.builder;

import me.jrandom.core.builder.markertype.Setter;
import me.jrandom.core.service.ReflectionFieldService;
import me.jrandom.core.service.impl.ReflectionService;

public class InstanceBuilder<T> {
  private T instance;
  private ReflectionFieldService reflectionFieldService = new ReflectionService();

  public InstanceBuilder(T instance) {
    this.instance = instance;
  }

  public <S> InstanceBuilder<T> set(Setter<T, S> setter, S value) {
    setter.accept(instance, value);
    return this;
  }

  public T build() {
    reflectionFieldService.setRandomValueToNullFields(instance);
    return instance;
  }
}
