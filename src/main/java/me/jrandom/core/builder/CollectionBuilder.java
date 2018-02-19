package me.jrandom.core.builder;

import java.util.Collection;

public class CollectionBuilder<T,S extends Collection<T>> {

  private S collection;

  public CollectionBuilder(S collection) {
    this.collection = collection;
  }

  public S build() {
    return collection;
  }
}
