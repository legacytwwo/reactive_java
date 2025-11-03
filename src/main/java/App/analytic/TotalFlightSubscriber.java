package App.analytic;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import App.entity.Flight;
import App.entity.FlightStatus;

public class TotalFlightSubscriber implements Subscriber<Flight> {

    private final long delay;
    private final AtomicLong counter = new AtomicLong(0);
    private final long batchSize;
    private Subscription subscription;

    private final CountDownLatch latch = new CountDownLatch(1);

    public TotalFlightSubscriber(long delay, long batchSize) {
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
        if (flight.getStatus(delay) == FlightStatus.LANDED) {
            counter.incrementAndGet();
        }
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

    public long getCount() {
        return counter.get();
    }

    public void await() throws InterruptedException {
        latch.await();
    }
}
