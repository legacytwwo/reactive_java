package App;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import App.analytic.FlightAnalytic;
import App.entity.Flight;
import App.generator.FlightGenerator;

public class Main {

    public static void main(String[] args) {
        long delay = 1;

        FlightGenerator generator = new FlightGenerator();
        FlightAnalytic analytics = new FlightAnalytic();

        int[] testCases = { 1000, 3000, 7000 };
        for (int size : testCases) {
            System.out.printf("test case %d elems start\n\n", size);
            List<Flight> flights = generator.generate(size);

            Instant start = Instant.now();
            analytics.totalFlightWithStream(flights, delay);
            long result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("total flight stream: %d ms\n", result);

            start = Instant.now();
            analytics.totalFlightWithParallelStream(flights, delay);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("total flight parallel stream: %d ms\n", result);

            start = Instant.now();
            analytics.totalFlightWithCustomSpliterator(flights, delay);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("total flight parallel spliterator stream: %d ms\n", result);



            start = Instant.now();
            analytics.totalDurationStream(flights, delay);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration stream: %d ms\n", result);

            start = Instant.now();
            analytics.totalDurationParallelStream(flights, delay);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration parallel stream: %d ms\n", result);

            start = Instant.now();
            analytics.totalDurationWithCustomSpliterator(flights, delay);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration parallel spliterator stream: %d ms\n", result);



            start = Instant.now();
            analytics.avgDurationPerTailNumberWithStream(flights, delay);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration per tail stream: %d ms\n", result);

            start = Instant.now();
            analytics.avgDurationPerTailNumberWithParallelStream(flights, delay);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration per tail parallel stream: %d ms\n", result);

            start = Instant.now();
            analytics.avgDurationPerTailNumberWithCustomSpliterator(flights, delay);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration per tail parallel spliterator stream: %d ms\n\n", result);



            // Instant start = Instant.now();
            // analytics.totalFlightWithLoop(flights);
            // long result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("total flight loop: %d ms\n", result);

            // start = Instant.now();
            // analytics.totalDurationWithLoop(flights, 0);
            // result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("avg duration loop: %d ms\n", result);

            // start = Instant.now();
            // analytics.avgDurationPerTailNumberWithLoop(flights, 0);
            // result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("avg duration per tail loop: %d ms\n\n", result);

            // // =================

            // start = Instant.now();
            // analytics.totalFlightWithStream(flights);
            // result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("total flight stream: %d ms\n", result);

            // start = Instant.now();
            // analytics.totalDurationStream(flights, 0);
            // result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("avg duration stream: %d ms\n", result);

            // start = Instant.now();
            // analytics.avgDurationPerTailNumberWithStream(flights, 0);
            // result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("avg duration per tail stream: %d ms\n\n", result);

            // // =================

            // start = Instant.now();
            // analytics.totalFlightWithCustomCollector(flights);
            // result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("total flight custom stream: %d ms\n", result);

            // start = Instant.now();
            // analytics.totalDurationWithCustomCollector(flights, 0);
            // result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("avg duration custom stream: %d ms\n", result);

            // start = Instant.now();
            // analytics.avgDurationPerTailNumberWithCustomCollector(flights, 0);
            // result = Duration.between(start, Instant.now()).toMillis();
            // System.out.printf("avg duration per tail custom stream: %d ms\n\n", result);
        }
    }
}