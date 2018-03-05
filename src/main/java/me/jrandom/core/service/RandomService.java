package me.jrandom.core.service;

import me.jrandom.core.builder.markertype.Setter;

public interface RandomService {

  <T, U> void setRandomValue(T instance, Setter<T, U> setter);
}
