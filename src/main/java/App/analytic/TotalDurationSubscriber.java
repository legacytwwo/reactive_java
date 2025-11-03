package App.analytic;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import App.entity.Flight;

public class TotalDurationSubscriber implements Subscriber<Flight> {

    private final long delay;
    private final long batchSize;
    private Subscription subscription;
    private final CountDownLatch latch = new CountDownLatch(1);

    private final AtomicLong totalDuration = new AtomicLong(0);
    private final AtomicLong flightCount = new AtomicLong(0);

    public TotalDurationSubscriber(long delay, long batchSize) {
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
        long duration = Duration.between(flight.getDepartureTime(delay), flight.getArrivalTime()).toMinutes();

        totalDuration.addAndGet(duration);
        flightCount.incrementAndGet();
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

    public double getAverageDuration() {
        long count = flightCount.get();
        if (count == 0) {
            return 0.0;
        }
        return (double) totalDuration.get() / count;
    }
}
