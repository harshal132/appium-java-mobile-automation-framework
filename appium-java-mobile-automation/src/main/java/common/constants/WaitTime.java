package common.constants;

public enum WaitTime {
    VVLOW(1000),
    LOW(2000),
    MEDIUM(5000),
    HIGH(8000),
    VHIGH(10000);

    private final int waitValue;
    private WaitTime(int value) {
        this.waitValue = value;
    }
    public int getTimeInMillis() {
        return waitValue;
    }

    public int getTimeInSeconds() {
        return waitValue/1000;
    }
}
