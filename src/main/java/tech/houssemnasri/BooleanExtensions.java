package tech.houssemnasri;

public interface BooleanExtensions {
  // NOT
  default boolean not(boolean bool) {
    return !bool;
  }

  // AND
  default boolean and(boolean a, boolean b) {
    return a && b;
  }

  default boolean and(boolean a, boolean b, boolean c) {
    return and(a, b) && c;
  }

  default boolean and(boolean a, boolean b, boolean c, boolean d) {
    return and(a, b, c) && d;
  }

  default boolean and(boolean a, boolean b, boolean c, boolean d, boolean e) {
    return and(a, b, c, d) && e;
  }
}
