package me.jrandom.core.builder;

import java.util.Collection;

public final class BuildingStrategyUtils {

  private BuildingStrategyUtils() {
  }

  public static <T, S> CollectionBuildingStrategy<Collection<T>, Setter<T, S>> all(S value) {
    return (Collection<T> instances, Setter<T, S> setter)
        -> instances.forEach(instance -> setter.accept(instance, value));
  }

  @SafeVarargs
  public static <T, S> CollectionBuildingStrategy<Collection<T>, Setter<T, S>> sequential(S... values) {
    return (Collection<T> instances, Setter<T, S> setter) -> {
      int counter = 0;
      for (T instance : instances) {
        S value = null;
        if(counter < values.length) {
          value = values[counter];
        }
        setter.accept(instance, value);
        counter++;
      }
    };
  }
}
