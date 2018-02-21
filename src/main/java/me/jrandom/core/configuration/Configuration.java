package me.jrandom.core.configuration;

public enum Configuration {
  INSTANCE;

  private DataGeneratorConfiguration configuration = new DataGeneratorConfiguration();

  public DataGeneratorConfiguration get() {
    return configuration;
  }

  public void set(DataGeneratorConfiguration configuration) {
    this.configuration = configuration;
  }
}
