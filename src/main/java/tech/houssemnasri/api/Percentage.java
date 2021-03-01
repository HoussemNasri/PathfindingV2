package tech.houssemnasri.api;

/** The {@code Percentage} class is used to deal with percentage of values */
public class Percentage {
    /** This is the value when the percentage is at {@code 100%} */
    private final double fullValue;

    /** This is the value after taking some percentage of {@code fullValue} */
    private double currentValue;

    public Percentage(final double fullValue, int initialPercentage) {
        this.fullValue = fullValue;
        updateValue(initialPercentage);
    }

    public Percentage(final double fullValue) {
        this(fullValue, 100);
    }

    /**
     * Returns the full value
     *
     * @return the value when percentage is at{@code 100%}
     */
    public double getFullValue() {
        return fullValue;
    }

    private void updateValue(int percent) {
        if (percent < 0 || percent > 100) {
            percent = Math.min(Math.max(percent, 0), 100);
        }
        currentValue = fullValue * (percent / 100.0d);
    }

    /**
     * Returns the percentage {@code percent} of the {@code fullValue}
     *
     * @return percentage from full value
     */
    public double getValue(int percent) {
        updateValue(percent);
        return currentValue;
    }

    @Override
    public String toString() {
        return String.format("%d%%", (int) (currentValue / fullValue) * 100);
    }
}
