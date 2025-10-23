package App.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Flight {
    private final String flightNumber;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final FlightStatus status;
    private final Airline airline;
    private final List<Passenger> passengers;

    private final Airport departureAirport;
    private final Airport arrivalAirport;
    private final Airplane airplane;

    public Flight(String flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
            FlightStatus status, Airline airline, List<Passenger> passengers,
            Airport departureAirport, Airport arrivalAirport, Airplane airplane) {
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.airline = airline;
        this.passengers = passengers;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.airplane = airplane;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public LocalDateTime getDepartureTime(long delay) {
        if (delay > 0) {
            double result = 0;
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < delay) {
                result = Math.tan(Math.atan(Math.random()));
            }
        }
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public FlightStatus getStatus(long delay) {
        if (delay > 0) {
            double result = 0;
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < delay) {
                result = Math.tan(Math.atan(Math.random()));
            }
        }
        return this.status;
    }

    public Airline getAirline() {
        return airline;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public Airplane getAirplane() {
        return airplane;
    }
}