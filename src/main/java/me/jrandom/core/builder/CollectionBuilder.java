package me.jrandom.core.builder;

import java.util.Collection;

import me.jrandom.core.builder.markertype.CollectionBuildingStrategy;
import me.jrandom.core.builder.markertype.Setter;
import me.jrandom.core.service.ReflectionFieldService;
import me.jrandom.core.service.impl.ReflectionService;

public class CollectionBuilder<T, V extends Collection<T>> {

  private V instanceCollection;
  private ReflectionFieldService reflectionFieldService = new ReflectionService();

  public CollectionBuilder(V instanceCollection) {
    this.instanceCollection = instanceCollection;
  }

  public <S> CollectionBuilder<T, V> set(Setter<T, S> setter, CollectionBuildingStrategy<Collection<T>, Setter<T, S>> strategy) {
    strategy.accept(instanceCollection, setter);
    return this;
  }

  public V build() {
    instanceCollection.forEach(reflectionFieldService::setRandomValueToNullFields);
    return instanceCollection;
  }
}
