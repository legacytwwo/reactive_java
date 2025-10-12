package App.analytic;

import App.entity.Flight;

public class TotalFlightsStatisticAccumulator {
    long totalFlight = 0;

    public void accumulate(Flight flight) {
        this.totalFlight++;
    }

    public TotalFlightsStatisticAccumulator combine(TotalFlightsStatisticAccumulator other) {
        this.totalFlight += other.totalFlight;
        return this;
    }
}
