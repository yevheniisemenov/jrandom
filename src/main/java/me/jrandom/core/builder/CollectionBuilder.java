package me.jrandom.core.builder;

import java.util.Collection;

public class CollectionBuilder<T, V extends Collection<T>> {

  private V collection;
  private BuilderCommons builderCommons = new BuilderCommons();

  public CollectionBuilder(V collection) {
    this.collection = collection;
  }

  public <S> CollectionBuilder<T, V> set(Setter<T, S> setter, CollectionBuildingStrategy<Collection<T>, Setter<T, S>> strategy) {
    strategy.accept(collection, setter);
    return this;
  }

  public V build() {
    collection.forEach(builderCommons::setRandomDataToFields);
    return collection;
  }
}
