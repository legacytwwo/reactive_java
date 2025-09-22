package App.analytic;

import java.time.Duration;
import java.util.List;
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.teeing;

import App.entity.Flight;
import App.entity.FlightStatistics;

public class FlightAnalytic {
    public FlightStatistics calculateWithLoop(List<Flight> flights) {
        long totalFlights = flights.size();
        long totalPassengers = 0;
        long totalDurationMinutes = 0;

        for (Flight flight : flights) {
            totalPassengers += flight.getPassengerNames().size();
            totalDurationMinutes += Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();
        }

        double averageDuration = (double) totalDurationMinutes / totalFlights;

        return new FlightStatistics(totalFlights, totalPassengers, averageDuration);
    }

    public FlightStatistics calculateWithStream(List<Flight> flights) {
        return flights.stream().collect(teeing(
                summingLong(flight -> flight.getPassengerNames().size()),
                averagingDouble(
                        flight -> Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes()),
                (totalPassengers, avgDuration) -> new FlightStatistics(
                        flights.size(),
                        totalPassengers,
                        avgDuration)));
    }

    public FlightStatistics calculateWithCustomCollector(List<Flight> flights) {
        return flights.stream().collect(new FlightStatisticsCollector(flights.size()));
    }
}