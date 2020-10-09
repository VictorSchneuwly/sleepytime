package schneuwly.victor.model;

import java.time.ZonedDateTime;

public final class Time {

    public static final int MAX_MINUTE = 60, MAX_HOUR = 24;

    private static final int NB_BITS_HOURS = 5, NB_BITS_MINUTES = 6;

    private final short packedHour;

    public Time(int hours, int minutes) throws IllegalArgumentException {
        if (!(0 <= minutes && minutes < 60 && 0 <= hours && hours < 24))
            throw new IllegalArgumentException(
                    String.format("This time : %02d:%02d is not a valid time.", hours, minutes)
            );

        packedHour = pack(hours, minutes);
    }

    public Time(int hours) {
        this(hours, 0);
    }

    public Time() {
        this(0, 0);
    }

    public int getHours() {
        return packedHour >> NB_BITS_MINUTES;
    }

    public int getMinutes() {
        return ((1 << NB_BITS_MINUTES) - 1) & packedHour;
    }

    public Time add(Time that) {
        return this.plusMinutes(that.getMinutes())
                .plusHours(that.getHours());
    }

    public Time plusMinutes(int minutes) {
        if (minutes < 0)
            throw new IllegalArgumentException("Minutes need to be positive.");

        int computedMinutes = minutes + getMinutes();
        int newMinute = Math.floorMod(computedMinutes, MAX_MINUTE);
        int newHour = Math.floorMod(getHours() + (computedMinutes / MAX_MINUTE), MAX_HOUR);


        return new Time(newHour, newMinute);
    }

    public Time plusHours(int hours) {
        if (hours < 0)
            throw new IllegalArgumentException("Hours need to be positive.");

        int newHour = Math.floorMod(getHours() + hours, MAX_HOUR);

        return new Time(newHour, getMinutes());
    }

    public Time plusTime(Time that) {
        return this.plusMinutes(that.getMinutes())
                .plusHours(that.getHours());
    }

    public Time minusMinutes(int minutes) {
        if (minutes < 0)
            throw new IllegalArgumentException("Input minutes need to be positive.");

        int computedMinutes = getMinutes() - minutes;
        int newMinute = Math.floorMod(computedMinutes, MAX_MINUTE);
        int newHour = (computedMinutes >= 0)
                ? getHours()
                : Math.floorMod(getHours() + (computedMinutes / MAX_MINUTE) - 1, MAX_HOUR);


        return new Time(newHour, newMinute);
    }

    public Time minusHours(int hours) {
        if (hours < 0)
            throw new IllegalArgumentException("Input hours need to be positive.");

        int newHour = Math.floorMod(getHours() - hours, MAX_HOUR);

        return new Time(newHour, getMinutes());
    }

    public Time minusTime(Time that) {
        return this.minusMinutes(that.getMinutes())
                .minusHours(that.getHours());
    }

    public static Time now() {
        return new Time(
                ZonedDateTime.now().getHour(),
                ZonedDateTime.now().getMinute()
        );
    }

    private short pack(int hours, int minutes) {
        return (short) ((hours << NB_BITS_MINUTES) | minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return packedHour == time.packedHour;
    }

    @Override
    public int hashCode() {
        return packedHour;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", getHours(), getMinutes());
    }
}
