package tech.houssemnasri.util;

public class MathUtils {

    /**
     * Clamps the given value between the given minimum float and maximum float values. Returns the
     * given value if it is within the min and max range.
     *
     * <p>Returns the min value if the given float value is less than the min. Returns the max value
     * if the given value is greater than the max value. Use Clamp to restrict a value to a range
     * that is defined by the min and max values.
     *
     * @param value floating point value to restrict inside the range defined by the min and max
     *     values.
     * @param min minimum floating point value to compare against.
     * @param max maximum floating point value to compare against.
     * @return value within min and max range
     */
    public static double clamp(double value, double min, double max) {
        if (Double.compare(value, min) < 0) return min;

        if (Double.compare(value, max) > 0) return max;

        return value;
    }
}
