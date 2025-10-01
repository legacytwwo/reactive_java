package App.entity;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightStatistics {
    private final long totalFlights;
    private final double overallAverageDurationMinutes;
    private final Map<String, Double> averageDurationPerTailNumber;

    public record TopAircraft(String tailNumber, long totalFlightMinutes) {
    }

    public FlightStatistics(long totalFlights, double overallAverageDurationMinutes,
            Map<String, Double> averageDurationPerTailNumber) {
        this.totalFlights = totalFlights;
        this.overallAverageDurationMinutes = overallAverageDurationMinutes;
        this.averageDurationPerTailNumber = averageDurationPerTailNumber;
    }

    public long getTotalFlights() {
        return totalFlights;
    }

    public Map<String, Double> getAverageDurationPerTailNumber() {
        return averageDurationPerTailNumber;
    }

    public double getOverallAverageDurationMinutes() {
        return overallAverageDurationMinutes;
    }

    public List<TopAircraft> getTopAircraftByAverageDuration(int limit) {
        return this.averageDurationPerTailNumber.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(entry -> new TopAircraft(entry.getKey(), entry.getValue().longValue()))
                .collect(Collectors.toList());
    }

}