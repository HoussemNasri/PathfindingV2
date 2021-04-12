package tech.houssemnasri.impl;

import java.util.Objects;

public enum ThemeDescriptor {
  DRACULA(
      "Dracula",
      Objects.requireNonNull(ThemeDescriptor.class.getResource("/theme/dracula.css")).toString()),
  LIGHTER(
      "Lighter",
      Objects.requireNonNull(ThemeDescriptor.class.getResource("/theme/lighter.css")).toString());
  private final String name;
  private final String path;

  ThemeDescriptor(String name, String path) {
    this.name = name;
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }
}
