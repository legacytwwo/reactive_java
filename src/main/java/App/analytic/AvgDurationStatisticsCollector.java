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

public class AvgDurationStatisticsCollector implements Collector<Flight, AvgDurationAccumulator, Map<String, Double>> {
    private long delay = 0;

    public AvgDurationStatisticsCollector(long delay) {
        this.delay = delay;
    }

    @Override
    public Supplier<AvgDurationAccumulator> supplier() {
        return () -> new AvgDurationAccumulator(this.delay);
    }

    @Override
    public BiConsumer<AvgDurationAccumulator, Flight> accumulator() {
        return AvgDurationAccumulator::accumulate;
    }

    @Override
    public BinaryOperator<AvgDurationAccumulator> combiner() {
        return AvgDurationAccumulator::combine;
    }

    @Override
    public Function<AvgDurationAccumulator, Map<String, Double>> finisher() {
        return acc -> {
            Map<String, Double> avgPerTail = new HashMap<>();
            acc.flightCounts.forEach((tailNumber, count) -> {
                long sum = acc.durationSums.get(tailNumber);
                avgPerTail.put(tailNumber, (double) sum / count);
            });
            return avgPerTail;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}