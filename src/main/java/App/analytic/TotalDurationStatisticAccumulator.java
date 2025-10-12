package App.analytic;

import java.time.Duration;

import App.entity.Flight;

public class TotalDurationStatisticAccumulator {
    long totalDurationMinutes = 0;

    public void accumulate(Flight flight) {
        long duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();
        this.totalDurationMinutes += duration;
    }

    public TotalDurationStatisticAccumulator combine(TotalDurationStatisticAccumulator other) {
        this.totalDurationMinutes += other.totalDurationMinutes;
        return this;
    }
}
