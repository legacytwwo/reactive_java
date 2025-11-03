package App.analytic;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import App.entity.Flight;

public class AvgDurationSubscriber implements Subscriber<Flight> {

    private static class StatsHolder {
        private final AtomicLong sum = new AtomicLong(0);
        private final AtomicInteger count = new AtomicInteger(0);

        void add(long value) {
            sum.addAndGet(value);
            count.incrementAndGet();
        }

        double average() {
            int currentCount = count.get();
            return currentCount == 0 ? 0.0 : (double) sum.get() / currentCount;
        }
    }

    private final long delay;
    private final long batchSize;
    private Subscription subscription;
    private final CountDownLatch latch = new CountDownLatch(1);

    private final Map<String, StatsHolder> stats = new ConcurrentHashMap<>();

    public AvgDurationSubscriber(long delay, long batchSize) {
        this.delay = delay;
        this.batchSize = batchSize;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        s.request(batchSize);
    }

    @Override
    public void onNext(Flight flight) {
        String tailNumber = flight.getAirplane().getTailNumber();
        long duration = Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime()).toMinutes();

        stats.computeIfAbsent(tailNumber, k -> new StatsHolder()).add(duration);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        latch.countDown();
    }

    @Override
    public void onComplete() {
        latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public Map<String, Double> getResult() {
        return stats.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().average()));
    }
}