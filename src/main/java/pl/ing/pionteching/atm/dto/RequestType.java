package pl.ing.pionteching.atm.dto;

public enum RequestType {
    STANDARD(0),
    SIGNAL_LOW(1),
    PRIORITY(2),
    FAILURE_RESTART(3);

    private final int priority;

    RequestType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}