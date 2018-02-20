package me.jrandom.core.builder;

import java.util.function.BiConsumer;

public interface CollectionBuildingStrategy<T, U> extends BiConsumer<T, U> {
}
