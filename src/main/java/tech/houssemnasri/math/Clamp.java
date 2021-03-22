package tech.houssemnasri.math;

import java.util.function.BiFunction;

public class Clamp implements BiFunction<Double, Double, Double> {
  private final double value;

  public Clamp(double value) {
    this.value = value;
  }

  @Override
  public Double apply(Double min, Double max) {
    if (Double.compare(value, min) < 0) return min;

    if (Double.compare(value, max) > 0) return max;

    return value;
  }
}
