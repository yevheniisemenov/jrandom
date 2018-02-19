package me.jrandom.core.builder;

import java.util.function.BiConsumer;

public class InstanceBuilder<T> {

  private T instance;

  public InstanceBuilder(T instance) {
    this.instance = instance;
  }

  public <S> InstanceBuilder<T> set(BiConsumer<T, S> setter, S value) {
    setter.accept(instance, value);
    return this;
  }

  public T build() {
    return instance;
  }
}
