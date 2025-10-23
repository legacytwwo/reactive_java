package App.analytic;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

import App.entity.Flight;
import App.entity.FlightStatus;

public class FlightAnalytic {
    public long totalFlightWithLoop(List<Flight> flights, long delay) {
        long totalLandedFlights = 0;

        for (Flight flight : flights) {
            FlightStatus flightStatus = flight.getStatus(delay);
            if (flightStatus == FlightStatus.LANDED) {
                totalLandedFlights++;
            }
        }

        return totalLandedFlights;
    }

    public double totalDurationWithLoop(List<Flight> flights, long delay) {
        long totalDurationMinutes = 0;

        for (Flight flight : flights) {
            long duration = Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime()).toMinutes();
            totalDurationMinutes += duration;
        }

        double overallAverage = (double) totalDurationMinutes / flights.size();
        return overallAverage;
    }

    public Map<String, Double> avgDurationPerTailNumberWithLoop(List<Flight> flights, long delay) {
        Map<String, Long> durationSums = new HashMap<>();
        Map<String, Long> flightsCounts = new HashMap<>();

        for (Flight flight : flights) {
            long duration = Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime()).toMinutes();
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

    public long totalFlightWithStream(List<Flight> flights, long delay) {
        return flights.stream()
                .filter(flight -> flight.getStatus(delay) == FlightStatus.LANDED)
                .count();
    }

    public double totalDurationStream(List<Flight> flights, long delay) {
        double overallAverage = flights.stream()
                .mapToLong(
                        flight -> Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime()).toMinutes())
                .average()
                .orElse(0.0);
        return overallAverage;
    }

    public Map<String, Double> avgDurationPerTailNumberWithStream(List<Flight> flights, long delay) {
        Map<String, Double> avgDurationPerTailNumber = flights.stream()
                .collect(groupingBy(
                        flight -> flight.getAirplane().getTailNumber(),
                        averagingDouble(
                                flight -> Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime())
                                        .toMinutes())));

        return avgDurationPerTailNumber;
    }

    public Long totalFlightWithCustomCollector(List<Flight> flights, long delay) {
        Long totalCount = flights.stream().collect(new TotalFlightStatisticsCollector(delay));
        return totalCount;
    }

    public Double totalDurationWithCustomCollector(List<Flight> flights, long delay) {
        Double totalDuration = flights.stream().collect(new TotalDurationStatisticsCollector(flights.size(), delay));
        return totalDuration;
    }

    public Map<String, Double> avgDurationPerTailNumberWithCustomCollector(List<Flight> flights, long delay) {
        Map<String, Double> avgDuration = flights.stream().collect(new AvgDurationStatisticsCollector(delay));
        return avgDuration;
    }

    public long totalFlightWithParallelStream(List<Flight> flights, long delay) {
        return flights.parallelStream()
                .filter(flight -> flight.getStatus(delay) == FlightStatus.LANDED)
                .count();
    }

    public double totalDurationParallelStream(List<Flight> flights, long delay) {
        double overallAverage = flights.parallelStream()
                .mapToLong(
                        flight -> Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime()).toMinutes())
                .average()
                .orElse(0.0);
        return overallAverage;
    }

    public Map<String, Double> avgDurationPerTailNumberWithParallelStream(List<Flight> flights, long delay) {
        Map<String, Double> avgDurationPerTailNumber = flights.parallelStream()
                .collect(groupingBy(
                        flight -> flight.getAirplane().getTailNumber(),
                        ConcurrentHashMap::new,
                        averagingDouble(
                                flight -> Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime())
                                        .toMinutes())));

        return avgDurationPerTailNumber;
    }




    public long totalFlightWithCustomSpliterator(List<Flight> flights, long delay) {
        Spliterator<Flight> spliterator = new FlightSpliterator(flights);
        Stream<Flight> stream = StreamSupport.stream(spliterator, true);

        return stream
                .filter(flight -> flight.getStatus(delay) == FlightStatus.LANDED)
                .count();
    }

    public double totalDurationWithCustomSpliterator(List<Flight> flights, long delay) {
        Spliterator<Flight> spliterator = new FlightSpliterator(flights);
        Stream<Flight> stream = StreamSupport.stream(spliterator, true);

        return stream
                .mapToLong(
                        flight -> Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime()).toMinutes())
                .average()
                .orElse(0.0);
    }

    public Map<String, Double> avgDurationPerTailNumberWithCustomSpliterator(List<Flight> flights, long delay) {
        Spliterator<Flight> spliterator = new FlightSpliterator(flights);
        Stream<Flight> stream = StreamSupport.stream(spliterator, true);

        return stream
                .collect(groupingBy(
                        flight -> flight.getAirplane().getTailNumber(),
                        averagingDouble(
                                flight -> Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime())
                                        .toMinutes())));
    }

    public Long totalFlightWithCustomCollectorSpliterator(List<Flight> flights, long delay) {
        Spliterator<Flight> spliterator = new FlightSpliterator(flights);
        Stream<Flight> stream = StreamSupport.stream(spliterator, true);

        Long totalCount = stream.collect(new TotalFlightStatisticsCollector(delay));
        return totalCount;
    }

    public Double totalDurationWithCustomCollectorSpliterator(List<Flight> flights, long delay) {
        Spliterator<Flight> spliterator = new FlightSpliterator(flights);
        Stream<Flight> stream = StreamSupport.stream(spliterator, true);

        Double totalDuration = stream.collect(new TotalDurationStatisticsCollector(flights.size(), delay));
        return totalDuration;
    }

    public Map<String, Double> avgDurationPerTailNumberWithCustomCollectorSpliterator(List<Flight> flights, long delay) {
        Spliterator<Flight> spliterator = new FlightSpliterator(flights);
        Stream<Flight> stream = StreamSupport.stream(spliterator, true);

        Map<String, Double> avgDuration = stream.collect(new AvgDurationStatisticsCollector(delay));
        return avgDuration;
    }
}