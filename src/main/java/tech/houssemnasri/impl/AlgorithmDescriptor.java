package tech.houssemnasri.impl;

public enum AlgorithmDescriptor {
  A_STAR(
      "A* search algorithm",
      "find the shortest path in a weighted graph using an heuristic to guide the process.");

  private final String name;
  private final String description;

  AlgorithmDescriptor(String name, String description) {
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
}
