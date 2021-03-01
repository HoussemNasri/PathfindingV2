package tech.houssemnasri.api;

public class Percentage {
    /** This is the value when the percentage is {@code 100%} */
    private final double fullValue;

    /** This is the value after taking some percentage of {@code fullValue} */
    private double currentValue;

    public Percentage(final double fullValue, int initialPercentage) {
        this.fullValue = fullValue;
        setPercentage(initialPercentage);
    }

    public Percentage(final double fullValue) {
        this(fullValue, 100);
    }

    public double getFullValue() {
        return fullValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setPercentage(int percent) {
        if (percent < 0 || percent > 100) {
            percent = Math.min(Math.max(percent, 0), 100);
        }
        currentValue = fullValue * (percent / 100.0d);
    }

    public int getPercentage() {
        return (int) (currentValue / fullValue) * 100;
    }

    public double setPercentageAndGet(int percent) {
        setPercentage(percent);
        return currentValue;
    }

    @Override
    public String toString() {
        return String.format("%d%%", getPercentage());
    }
}
