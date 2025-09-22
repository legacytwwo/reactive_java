package App.analytic;

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
            return new FlightStatistics(totalFlights, acc.totalPassengers, (double) acc.totalDurationMinutes / totalFlights);
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}