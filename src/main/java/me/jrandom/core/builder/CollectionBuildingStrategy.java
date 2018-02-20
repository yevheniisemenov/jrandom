package me.jrandom.core.builder;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface CollectionBuildingStrategy<T, U> extends BiConsumer<T, U> {
}
