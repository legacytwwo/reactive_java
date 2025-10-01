package App.analytic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import App.entity.Flight;
import App.entity.FlightStatistics;

public class FlightStatisticsCollector implements Collector<Flight, StatisticAccumulator, FlightStatistics> {

    private final long totalFlights;

    public FlightStatisticsCollector(long totalFlights) {
        this.totalFlights = totalFlights;
    }

    @Override
    public Supplier<StatisticAccumulator> supplier() {
        return StatisticAccumulator::new;
    }

    @Override
    public BiConsumer<StatisticAccumulator, Flight> accumulator() {
        return StatisticAccumulator::accumulate;
    }

    @Override
    public BinaryOperator<StatisticAccumulator> combiner() {
        return StatisticAccumulator::combine;
    }

    @Override
    public Function<StatisticAccumulator, FlightStatistics> finisher() {
        return acc -> {
            Map<String, Double> avgPerTail = new HashMap<>();
            acc.flightCounts.forEach((tailNumber, count) -> {
                long sum = acc.durationSums.get(tailNumber);
                avgPerTail.put(tailNumber, (double) sum / count);
            });
            return new FlightStatistics(totalFlights, (double) acc.totalDurationMinutes / totalFlights, avgPerTail);
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}