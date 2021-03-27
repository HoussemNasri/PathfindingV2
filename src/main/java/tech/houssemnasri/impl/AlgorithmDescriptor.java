package tech.houssemnasri.impl;

import java.util.Objects;

public class AlgorithmDescriptor {
  private final String name;
  private final String description;

  public AlgorithmDescriptor(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return String.format("Algorithm{%s}", name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AlgorithmDescriptor that = (AlgorithmDescriptor) o;
    return Objects.equals(getName(), that.getName())
        && Objects.equals(getDescription(), that.getDescription());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getDescription());
  }
}
