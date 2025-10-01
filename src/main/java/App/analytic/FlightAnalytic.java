package App.analytic;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

import App.entity.Flight;
import App.entity.FlightStatistics;

public class FlightAnalytic {
    public FlightStatistics calculateWithLoop(List<Flight> flights) {
        long totalFlights = flights.size();
        long totalDurationMinutes = 0;
        Map<String, Long> durationSums = new HashMap<>();
        Map<String, Long> flightsCounts = new HashMap<>();

        for (Flight flight : flights) {
            long duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();
            String tailNumber = flight.getAirplane().getTailNumber();
            durationSums.merge(tailNumber, duration, Long::sum);
            flightsCounts.merge(tailNumber, 1L, Long::sum);
            totalDurationMinutes += duration;
        }

        Map<String, Double> avgDurationPerTailNumber = new HashMap<>();
        for (String tailNumber : flightsCounts.keySet()) {
            double avg = (double) durationSums.get(tailNumber) / flightsCounts.get(tailNumber);
            avgDurationPerTailNumber.put(tailNumber, avg);
        }

        double overallAverage = (double) totalDurationMinutes / flights.size();
        return new FlightStatistics(totalFlights, overallAverage, avgDurationPerTailNumber);
    }

    public FlightStatistics calculateWithStream(List<Flight> flights) {
        double overallAverage = flights.stream()
                .mapToLong(flight -> Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes())
                .average()
                .orElse(0.0);

        Map<String, Double> avgDurationPerTailNumber = flights.stream()
                .collect(groupingBy(
                        flight -> flight.getAirplane().getTailNumber(),
                        averagingDouble(flight -> Duration.between(flight.getDepartureTime(), flight.getArrivalTime())
                                .toMinutes())));

        return new FlightStatistics(flights.size(), overallAverage, avgDurationPerTailNumber);
    }

    public FlightStatistics calculateWithCustomCollector(List<Flight> flights) {
        return flights.stream().collect(new FlightStatisticsCollector(flights.size()));
    }
}