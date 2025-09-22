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

        int[] testCases = {5000, 50000, 250000};
        for (int size : testCases) {
            System.out.printf("test case %d elems start\n", size);
            List<Flight> flights = generator.generate(size);

            Instant start = Instant.now();
            analytics.calculateWithLoop(flights);
            Instant end = Instant.now();
            System.out.printf("loop: %d ms\n", Duration.between(start, end).toMillis());

            start = Instant.now();
            analytics.calculateWithStream(flights);
            end = Instant.now();
            System.out.printf("stream: %d ms\n", Duration.between(start, end).toMillis());

            start = Instant.now();
            analytics.calculateWithCustomCollector(flights);
            end = Instant.now();
            System.out.printf("stream custom: %d ms\n", Duration.between(start, end).toMillis());
        }
    }
}