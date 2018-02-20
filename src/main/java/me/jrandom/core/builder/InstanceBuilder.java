package me.jrandom.core.builder;

import java.util.function.BiConsumer;

public class InstanceBuilder<T> {
  private T instance;
  private BuilderCommons builderCommons = new BuilderCommons();

  public InstanceBuilder(T instance) {
    this.instance = instance;
  }

  public <S> InstanceBuilder<T> set(BiConsumer<T, S> setter, S value) {
    setter.accept(instance, value);
    return this;
  }

  public T build() {
    builderCommons.setRandomDataToFields(instance);
    return instance;
  }
}
