package App.analytic;

import java.time.Duration;
import java.util.AbstractMap;
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
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    public Map<String, Double> avgDurationPerTailNumberWithCustomCollectorSpliterator(List<Flight> flights,
            long delay) {
        Spliterator<Flight> spliterator = new FlightSpliterator(flights);
        Stream<Flight> stream = StreamSupport.stream(spliterator, true);

        Map<String, Double> avgDuration = stream.collect(new AvgDurationStatisticsCollector(delay));
        return avgDuration;
    }

    public long totalFlightWithReactive(List<Flight> flights, long delay) {
        return Observable.fromIterable(flights)
                .flatMap(flight -> Observable.just(flight)
                        .subscribeOn(Schedulers.computation())
                        .filter(f -> f.getStatus(delay) == FlightStatus.LANDED))
                .count()
                .blockingGet();
    }

    public double totalDurationWithReactive(List<Flight> flights, long delay) {
        return Observable.fromIterable(flights)
                .flatMap(flight -> Observable.just(flight)
                        .subscribeOn(Schedulers.computation())
                        .map(f -> Duration.between(f.getDepartureTime(delay), f.getArrivalTime()).toMinutes()))
                .toList()
                .map(durations -> durations.stream()
                        .mapToLong(Long::longValue)
                        .average()
                        .orElse(0.0))
                .blockingGet();
    }

    private static class AverageAccumulator {
        double sum = 0;
        int count = 0;

        void add(double value) {
            sum += value;
            count++;
        }

        double average() {
            return count > 0 ? sum / count : 0.0;
        }
    }

    public Map<String, Double> avgDurationPerTailNumberWithReactive(List<Flight> flights, long delay) {
        return Observable.fromIterable(flights)
                .groupBy(flight -> flight.getAirplane().getTailNumber())
                .flatMapSingle(groupedObservable -> groupedObservable
                        .subscribeOn(Schedulers.computation())
                        .map(flight -> (double) Duration
                                .between(flight.getDepartureTime(delay), flight.getArrivalTime()).toMinutes())
                        .collect(AverageAccumulator::new, AverageAccumulator::add)
                        .map(AverageAccumulator::average)
                        .map(average -> new AbstractMap.SimpleEntry<>(groupedObservable.getKey(), average)))
                .toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)
                .blockingGet();
    }

    public long totalFlightWithReactiveFlowable(Flowable<Flight> flights, long delay, long batchSize)
            throws InterruptedException {
        TotalFlightSubscriber subscriber = new TotalFlightSubscriber(delay, batchSize);

        flights
                .subscribeOn(Schedulers.computation())
                .subscribeWith(subscriber);

        subscriber.await();

        return subscriber.getCount();
    }

    public double totalDurationWithReactiveFlowable(Flowable<Flight> flights, long delay, long batchSize)
            throws InterruptedException {
        TotalDurationSubscriber subscriber = new TotalDurationSubscriber(delay, batchSize);

        flights
                .subscribeOn(Schedulers.computation())
                .subscribe(subscriber);

        subscriber.await();

        return subscriber.getAverageDuration();
    }

    public Map<String, Double> avgDurationPerTailNumberWithReactiveFlowable(Flowable<Flight> flights, long delay,
            long batchSize) throws InterruptedException {
        AvgDurationSubscriber subscriber = new AvgDurationSubscriber(delay, batchSize);

        flights
                .subscribeOn(Schedulers.computation())
                .subscribe(subscriber);

        subscriber.await();

        return subscriber.getResult();
    }
}