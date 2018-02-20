package me.jrandom.core.builder;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface Setter<T, U> extends BiConsumer<T, U> {
}
