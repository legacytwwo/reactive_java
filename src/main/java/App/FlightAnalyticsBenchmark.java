package App;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import App.analytic.FlightAnalytic;
import App.entity.Flight;
import App.generator.FlightGenerator;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 3, time = 1)
public class FlightAnalyticsBenchmark {

    @Param({"1000", "3000", "5000"})
    private int listSize;

    private List<Flight> flights;
    private FlightAnalytic analytics;
    private final long delay = 0;

    @Setup(Level.Trial)
    public void setup() {
        FlightGenerator generator = new FlightGenerator();
        this.analytics = new FlightAnalytic();
        this.flights = generator.generate(listSize);
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FlightAnalyticsBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
    
    @Benchmark
    public long totalFlight_Stream() {
        return analytics.totalFlightWithStream(flights, delay);
    }

    @Benchmark
    public long totalFlight_ParallelStream() {
        return analytics.totalFlightWithParallelStream(flights, delay);
    }

    @Benchmark
    public long totalFlight_CustomSpliterator() {
        return analytics.totalFlightWithCustomCollectorSpliterator(flights, delay);
    }

    @Benchmark
    public double avgDuration_Stream() {
        return analytics.totalDurationStream(flights, delay);
    }

    @Benchmark
    public double avgDuration_ParallelStream() {
        return analytics.totalDurationParallelStream(flights, delay);
    }

    @Benchmark
    public double avgDuration_CustomSpliterator() {
        return analytics.totalDurationWithCustomCollectorSpliterator(flights, delay);
    }
    
    @Benchmark
    public Map<String, Double> groupBy_Stream() {
        return analytics.avgDurationPerTailNumberWithStream(flights, delay);
    }

    @Benchmark
    public Map<String, Double> groupBy_ParallelStream_Concurrent() {
        return analytics.avgDurationPerTailNumberWithParallelStream(flights, delay);
    }

    @Benchmark
    public Map<String, Double> groupBy_CustomSpliterator() {
        return analytics.avgDurationPerTailNumberWithCustomCollectorSpliterator(flights, delay);
    }
}