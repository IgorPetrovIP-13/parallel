package tpo1.t6;

public class Incrementer implements Runnable {
    private CounterInterface counter;

    public Incrementer(CounterInterface counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.increment();
        }
    }
}
