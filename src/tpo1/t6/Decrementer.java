package tpo1.t6;

public class Decrementer implements Runnable {
    private CounterInterface counter;

    public Decrementer(CounterInterface counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.decrement();
        }
    }
}
