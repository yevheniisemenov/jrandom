package me.jrandom.core.builder;

import me.jrandom.core.builder.markertype.Setter;
import me.jrandom.core.service.RandomService;
import me.jrandom.core.service.impl.RandomServiceImpl;

public class InstanceBuilder<T> {
  private final T instance;
  private final RandomService randomService = new RandomServiceImpl();

  public InstanceBuilder(T instance) {
    this.instance = instance;
  }

  public <S> InstanceBuilder<T> set(Setter<T, S> setter, S value) {
    setter.accept(instance, value);
    return this;
  }

  @SafeVarargs
  public final <S> InstanceBuilder<T> random(Setter<T, S>... setters) {
    for (Setter<T, S> setter : setters) {
      randomService.setRandomValue(instance, setter);
    }
    return this;
  }

  public T build() {
    return instance;
  }
}
