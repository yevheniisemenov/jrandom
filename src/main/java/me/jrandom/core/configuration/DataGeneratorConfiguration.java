package me.jrandom.core.configuration;

import java.util.HashSet;
import java.util.Set;

public class DataGeneratorConfiguration {

  private Set<Mapper> mappers = new HashSet<>();

  public Set<Mapper> getMappers() {
    return mappers;
  }

  public  <T> Mapper getMapper(Class<T> clazz) {
    return mappers.stream()
        .filter(mapper -> mapper.getForClass().equals(clazz))
        .findFirst()
        .orElse(Mapper.EMPTY_MAPPER);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Set<Mapper> mappers = new HashSet<>();

    Builder() {
    }

    public Builder withMappers(Set<Mapper> mappers) {
      this.mappers.addAll(mappers);
      return this;
    }

    public Builder withMapper(Mapper mapper) {
      this.mappers.add(mapper);
      return this;
    }

    public DataGeneratorConfiguration build() {
      DataGeneratorConfiguration dataGeneratorConfiguration = new DataGeneratorConfiguration();
      dataGeneratorConfiguration.mappers = this.mappers;
      return dataGeneratorConfiguration;
    }
  }
}
