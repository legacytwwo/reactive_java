package App.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Flight {
    private final long id;
    private final String flightNumber;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final FlightStatus status;
    private final Airline airline;
    private final List<String> passengerNames;

    private final Airport departureAirport;
    private final Airport arrivalAirport;
    private final Airplane airplane;

    public Flight(long id, String flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
            FlightStatus status, Airline airline, List<String> passengerNames,
            Airport departureAirport, Airport arrivalAirport, Airplane airplane) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.airline = airline;
        this.passengerNames = passengerNames;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.airplane = airplane;
    }

    public long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public Airline getAirline() {
        return airline;
    }

    public List<String> getPassengerNames() {
        return passengerNames;
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

    @Override
    public String toString() {
        return String.format(
                "--- Рейс %s (%s) ---\n" +
                        "  Статус: %s\n" +
                        "  Маршрут: %s -> %s\n" +
                        "  Вылет: %s\n" +
                        "  Прибытие: %s\n" +
                        "  Самолет: %s\n" +
                        "  Количество пассажиров: %d\n",
                this.flightNumber, this.airline.name(),
                this.status,
                this.departureAirport.getName(), this.arrivalAirport.getName(),
                this.departureTime, this.arrivalTime,
                this.airplane.getModel(),
                this.passengerNames.size());
    }
}