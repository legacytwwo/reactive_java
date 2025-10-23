package App.generator;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import App.entity.Airline;
import App.entity.Airplane;
import App.entity.Flight;
import App.entity.FlightStatus;
import App.entity.Passenger;

public class FlightGenerator {
    private final Random random = new Random();
    private final AirportGenerator airportGenerator = new AirportGenerator();
    private final AirplaneGenerator airplaneGenerator = new AirplaneGenerator();
    private final Faker faker = new Faker();

    private static final List<Airline> AIRLINES = List.of(
            new Airline("SU", "Аэрофлот"),
            new Airline("S7", "S7 Airlines"),
            new Airline("DP", "Победа"));

    public List<Flight> generate(int count) {
        List<Flight> flights = new ArrayList<>();
        List<Airplane> airplanePool = airplaneGenerator.generateFromSize(count / 100);

        for (int i = 0; i < count; i++) {
            Airplane airplane = airplanePool.get(random.nextInt(airplanePool.size()));
            Flight newFlight = createRandomFlight(airplane);
            flights.add(newFlight);
        }

        return flights;
    }

    private Flight createRandomFlight(Airplane airplane) {
        Airline airline = AIRLINES.get(random.nextInt(AIRLINES.size()));
        String flightNumber = airline.code() + "-" + (1000 + random.nextInt(8999));

        AirportGenerator.AirportPair airportPair = airportGenerator.generateAirportPair();

        LocalDateTime departureTime = LocalDateTime.now().plusHours(random.nextInt(72));
        long flightDurationMinutes = 120 + random.nextInt(180);
        LocalDateTime arrivalTime = departureTime.plusMinutes(flightDurationMinutes);

        int passengerCount = 50 + random.nextInt(airplane.getPassengerCapacity() - 50);
        List<Passenger> passengers = IntStream.range(0, passengerCount)
                .mapToObj(_ -> new Passenger(
                    "faker.name().fullName()",
                    50,
                    ""))
                    // faker.name().fullName(),
                    // faker.number().numberBetween(1, 99),
                    // faker.phoneNumber().phoneNumber()))
                .collect(Collectors.toList());

        return new Flight(
                flightNumber,
                departureTime,
                arrivalTime,
                FlightStatus.values()[random.nextInt(FlightStatus.values().length)],
                airline,
                passengers,
                airportPair.arrival(),
                airportPair.departure(),
                airplane);
    }
}