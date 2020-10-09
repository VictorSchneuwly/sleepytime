package schneuwly.victor.model;

import java.util.ArrayList;
import java.util.List;

public final class SleepCalculator {
    private SleepCalculator() {
    }

    public static final List<Time> CYCLES = List.of(
            new Time(1, 30),
            new Time(3),
            new Time(4, 30),
            new Time(6),
            new Time(7, 30),
            new Time(9)
    );

    public static final int ASLEEP_TIME = 14;

    public static List<Time> wakeUpTimes(Time bedTime) {
        Time sleepTime = bedTime.plusMinutes(ASLEEP_TIME);
        List<Time> wakeUpTimes = new ArrayList<>(CYCLES.size());

        for (Time t : CYCLES) {
            wakeUpTimes.add(sleepTime.plusTime(t));
        }

        return List.copyOf(wakeUpTimes);

    }

    public static List<Time> bedTimes(Time wakeUpTime) {
        List<Time> bedTimes = new ArrayList<>(CYCLES.size());

        for (int i = CYCLES.size() - 1; i > 1; i--) {
            bedTimes.add(wakeUpTime.minusTime(CYCLES.get(i))
                    .minusMinutes(ASLEEP_TIME));
        }

        return List.copyOf(bedTimes);

    }

}
