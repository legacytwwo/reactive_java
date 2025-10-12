package App;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import App.analytic.FlightAnalytic;
import App.entity.Flight;
import App.generator.FlightGenerator;

public class Main {

    public static void main(String[] args) {
        FlightGenerator generator = new FlightGenerator();
        FlightAnalytic analytics = new FlightAnalytic();

        int[] testCases = { 5000, 50000, 250000 };
        for (int size : testCases) {
            System.out.printf("test case %d elems start\n\n", size);
            List<Flight> flights = generator.generate(size);

            Instant start = Instant.now();
            analytics.totalFlightWithLoop(flights);
            long result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("total flight loop: %d ms\n", result);

            start = Instant.now();
            analytics.totalDurationWithLoop(flights);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration loop: %d ms\n", result);

            start = Instant.now();
            analytics.avgDurationPerTailNumberWithLoop(flights);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration per tail loop: %d ms\n\n", result);

            // =================

            start = Instant.now();
            analytics.totalFlightWithStream(flights);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("total flight stream: %d ms\n", result);

            start = Instant.now();
            analytics.totalDurationStream(flights);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration stream: %d ms\n", result);

            start = Instant.now();
            analytics.avgDurationPerTailNumberWithStream(flights);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration per tail stream: %d ms\n\n", result);

            // =================

            start = Instant.now();
            analytics.totalFlightWithCustomCollector(flights);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("total flight custom stream: %d ms\n", result);

            start = Instant.now();
            analytics.totalDurationWithCustomCollector(flights);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration custom stream: %d ms\n", result);

            start = Instant.now();
            analytics.avgDurationPerTailNumberWithCustomCollector(flights);
            result = Duration.between(start, Instant.now()).toMillis();
            System.out.printf("avg duration per tail custom stream: %d ms\n\n", result);
        }
    }
}