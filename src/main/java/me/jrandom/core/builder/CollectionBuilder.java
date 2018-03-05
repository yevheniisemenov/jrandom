package me.jrandom.core.builder;

import java.util.Collection;

import me.jrandom.core.builder.markertype.CollectionBuildingStrategy;
import me.jrandom.core.builder.markertype.Setter;
import me.jrandom.core.service.RandomService;
import me.jrandom.core.service.impl.RandomServiceImpl;

public class CollectionBuilder<T, V extends Collection<T>> {

  private final V instanceCollection;
  private final RandomService randomService = new RandomServiceImpl();

  public CollectionBuilder(V instanceCollection) {
    this.instanceCollection = instanceCollection;
  }

  public <S> CollectionBuilder<T, V> set(Setter<T, S> setter, CollectionBuildingStrategy<Collection<T>, Setter<T, S>> strategy) {
    strategy.accept(instanceCollection, setter);
    return this;
  }

  @SafeVarargs
  public final <S> CollectionBuilder<T, V> random(Setter<T, S>... setters) {
    for (Setter<T, S> setter : setters) {
      for (T instance : instanceCollection) {
        randomService.setRandomValue(instance, setter);
      }
    }
    return this;
  }

  public V build() {
    return instanceCollection;
  }
}
