package App.analytic;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import App.entity.Flight;

public class TotalFlightStatisticsCollector implements Collector<Flight, TotalFlightsStatisticAccumulator, Long> {
    private long delay = 0;

    public TotalFlightStatisticsCollector(long delay) {
        this.delay = delay;
    }

    @Override
    public Supplier<TotalFlightsStatisticAccumulator> supplier() {
        return () -> new TotalFlightsStatisticAccumulator(this.delay);
    }

    @Override
    public BiConsumer<TotalFlightsStatisticAccumulator, Flight> accumulator() {
        return TotalFlightsStatisticAccumulator::accumulate;
    }

    @Override
    public BinaryOperator<TotalFlightsStatisticAccumulator> combiner() {
        return TotalFlightsStatisticAccumulator::combine;
    }

    @Override
    public Function<TotalFlightsStatisticAccumulator, Long> finisher() {
        return acc -> {
            return acc.totalFlight;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
