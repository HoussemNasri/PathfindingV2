package tech.houssemnasri.api;

public final class CostEntity {
    private final int[] costArguments;

    public CostEntity(final int[] costArguments) {
        this.costArguments = costArguments;
    }

    public int[] getCostArguments() {
        return costArguments;
    }
}
