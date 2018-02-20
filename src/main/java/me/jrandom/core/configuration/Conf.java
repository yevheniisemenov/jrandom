package me.jrandom.core.configuration;

public enum Conf {
  INSTANCE;

  private DataGeneratorConfiguration configuration = new DataGeneratorConfiguration();

  public DataGeneratorConfiguration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(DataGeneratorConfiguration configuration) {
    this.configuration = configuration;
  }
}
