package App.analytic;

import java.time.Duration;

import App.entity.Flight;

public class StatisticAccumulator {
    long totalPassengers = 0;
    long totalDurationMinutes = 0;

    public void accumulate(Flight flight) {
        this.totalPassengers += flight.getPassengerNames().size();
        this.totalDurationMinutes += Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();
    }

    public StatisticAccumulator combine(StatisticAccumulator other) {
        this.totalPassengers += other.totalPassengers;
        this.totalDurationMinutes += other.totalDurationMinutes;
        return this;
    }
}