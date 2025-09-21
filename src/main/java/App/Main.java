package App;

import java.util.List;

import App.entity.Flight;
import App.generator.FlightGenerator;

public class Main {

    public static void main(String[] args) {
        FlightGenerator flightGenerator = new FlightGenerator();

        List<Flight> flights = flightGenerator.generate(10);

        flights.forEach(System.out::println);
    }
}