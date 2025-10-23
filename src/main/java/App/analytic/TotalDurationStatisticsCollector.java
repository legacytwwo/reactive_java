package App.analytic;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import App.entity.Flight;

public class TotalDurationStatisticsCollector implements Collector<Flight, TotalDurationStatisticAccumulator, Double> {
    private long delay = 0;
    private long totalFlights = 0;

    public TotalDurationStatisticsCollector(long totalFlights, long delay) {
        this.delay = delay;
        this.totalFlights = totalFlights;
    }

    @Override
    public Supplier<TotalDurationStatisticAccumulator> supplier() {
        return () -> new TotalDurationStatisticAccumulator(this.delay);
    }

    @Override
    public BiConsumer<TotalDurationStatisticAccumulator, Flight> accumulator() {
        return TotalDurationStatisticAccumulator::accumulate;
    }

    @Override
    public BinaryOperator<TotalDurationStatisticAccumulator> combiner() {
        return TotalDurationStatisticAccumulator::combine;
    }

    @Override
    public Function<TotalDurationStatisticAccumulator, Double> finisher() {
        return acc -> {
            Double overallAverageDurationMinutes = (double) acc.totalDurationMinutes / this.totalFlights;
            return overallAverageDurationMinutes;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
