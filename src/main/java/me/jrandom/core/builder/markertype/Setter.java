package me.jrandom.core.builder.markertype;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface Setter<T, U> extends BiConsumer<T, U> {
}
