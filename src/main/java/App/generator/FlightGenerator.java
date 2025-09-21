package App.generator;

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

public class FlightGenerator {
    private final Random random = new Random();
    private final AirportGenerator airportGenerator = new AirportGenerator();
    private final AirplaneGenerator airplaneGenerator = new AirplaneGenerator();

    private static final List<Airline> AIRLINES = List.of(
            new Airline("SU", "Аэрофлот"),
            new Airline("S7", "S7 Airlines"),
            new Airline("DP", "Победа"));

    public List<Flight> generate(int count) {
        List<Flight> flights = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Flight newFlight = createRandomFlight();
            flights.add(newFlight);
        }

        return flights;
    }

    private Flight createRandomFlight() {
        Airline airline = AIRLINES.get(random.nextInt(AIRLINES.size()));
        String flightNumber = airline.code() + "-" + (1000 + random.nextInt(8999));

        AirportGenerator.AirportPair airportPair = airportGenerator.generateAirportPair();

        Airplane airplane = airplaneGenerator.generate();

        LocalDateTime departureTime = LocalDateTime.now().plusHours(random.nextInt(72));
        long flightDurationMinutes = 120 + random.nextInt(180);
        LocalDateTime arrivalTime = departureTime.plusMinutes(flightDurationMinutes);

        int passengerCount = 50 + random.nextInt(airplane.getPassengerCapacity() - 50);

        List<String> passengers = new ArrayList<>();
        for (int i = 1; i <= passengerCount; i++) {
            String passengerName = "Passenger " + i;
            passengers.add(passengerName);
        }

        return new Flight(
                Math.abs(random.nextLong()),
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