package App.generator;

import java.util.List;
import java.util.Random;

import App.entity.Airport;

public class AirportGenerator {
    public record AirportPair(Airport departure, Airport arrival) {
    }

    private static final List<Airport> AIRPORTS = List.of(
            new Airport("SVO", "Шереметьево", "Москва"),
            new Airport("LED", "Пулково", "Санкт-Петербург"),
            new Airport("AER", "Сочи", "Адлер"),
            new Airport("OVB", "Толмачёво", "Новосибирск"),
            new Airport("SVX", "Кольцово", "Екатеринбург"));
    private static final Random random = new Random();

    private Airport generateRandomAirport() {
        return AIRPORTS.get(random.nextInt(AIRPORTS.size()));
    }

    public AirportPair generateAirportPair() {
        Airport departure = generateRandomAirport();
        Airport arrival;

        do {
            arrival = generateRandomAirport();
        } while (arrival.equals(departure));

        return new AirportPair(departure, arrival);
    }
}
