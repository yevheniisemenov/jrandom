package me.jrandom.core.configuration;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

import me.jrandom.core.type.RandomGenerator;

public class Mapper {
  public static final Mapper EMPTY_MAPPER = Mapper.forClass(Object.class).build();

  private Class forClass;
  private Map<String, RandomGenerator> generatorMapping = new HashMap<>();

  private Mapper() {
  }

  public Class getForClass() {
    return forClass;
  }

  public Map<String, RandomGenerator> getGeneratorMapping() {
    return generatorMapping;
  }

  public static Builder forClass(Class forClass) {
    Builder builder = new Builder();
    builder.forClass = forClass;

    return builder;
  }

  public static final class Builder {
    private Class forClass;
    private Map<String, RandomGenerator> generatorMapping = new HashMap<>();

    Builder() {
    }

    public Builder addMapping(String field, RandomGenerator generator) {
      this.generatorMapping.put(field, generator);
      return this;
    }

    public Mapper build() {
      Mapper mapper = new Mapper();
      mapper.generatorMapping = this.generatorMapping;
      mapper.forClass = this.forClass;
      return mapper;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Mapper mapper = (Mapper) o;

    return new EqualsBuilder()
        .append(forClass, mapper.forClass)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(forClass)
        .toHashCode();
  }
}
