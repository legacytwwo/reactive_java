package App.analytic;

import java.time.Duration;

import App.entity.Flight;

public class TotalDurationStatisticAccumulator {
    long delay = 0;
    long totalDurationMinutes = 0;

    public TotalDurationStatisticAccumulator(long delay) {
        this.delay = delay;
    }

    public void accumulate(Flight flight) {
        long duration = Duration.between(flight.getDepartureTime(this.delay), flight.getArrivalTime()).toMinutes();
        this.totalDurationMinutes += duration;
    }

    public TotalDurationStatisticAccumulator combine(TotalDurationStatisticAccumulator other) {
        this.totalDurationMinutes += other.totalDurationMinutes;
        return this;
    }
}
