package App.analytic;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import App.entity.Flight;

public class StatisticAccumulator {
    long totalDurationMinutes = 0;
    Map<String, Long> durationSums = new HashMap<>();
    Map<String, Long> flightCounts = new HashMap<>();

    public void accumulate(Flight flight) {
        long duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();
        String tailNumber = flight.getAirplane().getTailNumber();
        this.totalDurationMinutes += duration;
        this.durationSums.merge(tailNumber, duration, Long::sum);
        this.flightCounts.merge(tailNumber, 1L, Long::sum);
    }

    public StatisticAccumulator combine(StatisticAccumulator other) {
        this.totalDurationMinutes += other.totalDurationMinutes;
        other.durationSums.forEach((tailNumber, sum) -> {
            this.durationSums.merge(tailNumber, sum, Long::sum);
        });
        other.flightCounts.forEach((tailNumber, count) -> {
            this.flightCounts.merge(tailNumber, count, Long::sum);
        });
        return this;
    }
}