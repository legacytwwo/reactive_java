package App.entity;

public class FlightStatistics {
    private final long totalFlights;
    private final long totalPassengers;
    private final double averageFlightDurationMinutes;

    public FlightStatistics(long totalFlights, long totalPassengers, double averageFlightDurationMinutes) {
        this.totalFlights = totalFlights;
        this.totalPassengers = totalPassengers;
        this.averageFlightDurationMinutes = averageFlightDurationMinutes;
    }

    public long getTotalFlights() {
        return totalFlights;
    }

    public long getTotalPassengers() {
        return totalPassengers;
    }

    public double getAverageFlightDurationMinutes() {
        return averageFlightDurationMinutes;
    }
}