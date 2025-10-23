package App.analytic;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import App.entity.Flight;

public class AvgDurationAccumulator {
    long delay = 0;
    long totalFlight = 0;
    Map<String, Long> durationSums = new HashMap<>();
    Map<String, Long> flightCounts = new HashMap<>();

    public AvgDurationAccumulator(long delay) {
        this.delay = delay;
    }

    public void accumulate(Flight flight) {
        this.totalFlight++;
        long duration = Duration.between(flight.getDepartureTime(this.delay), flight.getArrivalTime()).toMinutes();
        String tailNumber = flight.getAirplane().getTailNumber();
        this.durationSums.merge(tailNumber, duration, Long::sum);
        this.flightCounts.merge(tailNumber, 1L, Long::sum);
    }

    public AvgDurationAccumulator combine(AvgDurationAccumulator other) {
        other.durationSums.forEach((tailNumber, sum) -> {
            this.durationSums.merge(tailNumber, sum, Long::sum);
        });
        other.flightCounts.forEach((tailNumber, count) -> {
            this.flightCounts.merge(tailNumber, count, Long::sum);
        });
        return this;
    }
}
