package tpo1.t6;

public class Main {
    public static void main(String[] args)  {
        runCounter(new Counter());
        runCounter(new CounterSync());
        runCounter(new CounterLock());
        runCounter(new CounterSyncBlock());
    }

    private static void runCounter(CounterInterface counter) {
        Incrementer incrementer = new Incrementer(counter);
        Decrementer decrementer = new Decrementer(counter);

        Thread incrementThread = new Thread(incrementer);
        Thread decrementThread = new Thread(decrementer);

        incrementThread.start();
        decrementThread.start();

        try {
            incrementThread.join();
            decrementThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Final count of " + counter.getClass().getSimpleName() + " = " + counter.getCount());
    }
}
