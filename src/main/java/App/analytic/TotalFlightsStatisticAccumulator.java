package App.analytic;

import App.entity.Flight;
import App.entity.FlightStatus;

public class TotalFlightsStatisticAccumulator {
    long delay = 0;
    long totalFlight = 0;

    public TotalFlightsStatisticAccumulator(long delay) {
        this.delay = delay;
    }

    public void accumulate(Flight flight) {
        if (flight.getStatus(delay) == FlightStatus.LANDED) {
            this.totalFlight++;
        }
    }

    public TotalFlightsStatisticAccumulator combine(TotalFlightsStatisticAccumulator other) {
        this.totalFlight += other.totalFlight;
        return this;
    }
}
