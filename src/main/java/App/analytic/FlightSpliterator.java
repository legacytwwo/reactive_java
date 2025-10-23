package App.analytic;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import App.entity.Flight;

public class FlightSpliterator implements Spliterator<Flight> {

    private final List<Flight> flights;
    private int origin;
    private final int fence;

    public FlightSpliterator(List<Flight> flights) {
        this(flights, 0, flights.size());
    }

    private FlightSpliterator(List<Flight> flights, int origin, int fence) {
        this.flights = flights;
        this.origin = origin;
        this.fence = fence;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Flight> action) {
        if (origin < fence) {
            action.accept(flights.get(origin));
            origin++;
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<Flight> trySplit() {
        int lo = origin;
        int mid = (lo + fence) >>> 1;

        if (lo >= mid) {
            return null;
        }

        FlightSpliterator newSpliterator = new FlightSpliterator(flights, lo, mid);
        
        this.origin = mid;
        
        return newSpliterator;
    }

    @Override
    public long estimateSize() {
        return (long) (fence - origin);
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | IMMUTABLE | NONNULL;
    }
}