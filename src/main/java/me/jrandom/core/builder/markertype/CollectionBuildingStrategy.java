package me.jrandom.core.builder.markertype;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface CollectionBuildingStrategy<T, U> extends BiConsumer<T, U> {
}
