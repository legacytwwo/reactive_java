package App.analytic;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

import App.entity.Flight;

public class FlightAnalytic {
    public long totalFlightWithLoop(List<Flight> flights) {
        long totalFlights = 0;

        for (Flight _ : flights) {
            totalFlights++;
        }

        return totalFlights;
    }

    public double totalDurationWithLoop(List<Flight> flights) {
        long totalDurationMinutes = 0;

        for (Flight flight : flights) {
            long duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();
            totalDurationMinutes += duration;
        }

        double overallAverage = (double) totalDurationMinutes / flights.size();
        return overallAverage;
    }

    public Map<String, Double> avgDurationPerTailNumberWithLoop(List<Flight> flights) {
        Map<String, Long> durationSums = new HashMap<>();
        Map<String, Long> flightsCounts = new HashMap<>();

        for (Flight flight : flights) {
            long duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();
            String tailNumber = flight.getAirplane().getTailNumber();
            durationSums.merge(tailNumber, duration, Long::sum);
            flightsCounts.merge(tailNumber, 1L, Long::sum);
        }

        Map<String, Double> avgDurationPerTailNumber = new HashMap<>();
        for (String tailNumber : flightsCounts.keySet()) {
            double avg = (double) durationSums.get(tailNumber) / flightsCounts.get(tailNumber);
            avgDurationPerTailNumber.put(tailNumber, avg);
        }

        return avgDurationPerTailNumber;
    }

    public long totalFlightWithStream(List<Flight> flights) {
        return flights.stream().mapToInt(_ -> 1).sum();
    }

    public double totalDurationStream(List<Flight> flights) {
        double overallAverage = flights.stream()
                .mapToLong(flight -> Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes())
                .average()
                .orElse(0.0);
        return overallAverage;
    }

    public Map<String, Double> avgDurationPerTailNumberWithStream(List<Flight> flights) {
        Map<String, Double> avgDurationPerTailNumber = flights.stream()
                .collect(groupingBy(
                        flight -> flight.getAirplane().getTailNumber(),
                        averagingDouble(flight -> Duration.between(flight.getDepartureTime(), flight.getArrivalTime())
                                .toMinutes())));

        return avgDurationPerTailNumber;
    }

    public Long totalFlightWithCustomCollector(List<Flight> flights) {
        Long totalCount = flights.stream().collect(new TotalFlightStatisticsCollector());
        return totalCount;
    }

    public Double totalDurationWithCustomCollector(List<Flight> flights) {
        Double totalDuration = flights.stream().collect(new TotalDurationStatisticsCollector(flights.size()));
        return totalDuration;
    }

    public Map<String, Double> avgDurationPerTailNumberWithCustomCollector(List<Flight> flights) {
        Map<String, Double> avgDuration = flights.stream().collect(new AvgDurationStatisticsCollector());
        return avgDuration;
    }
}