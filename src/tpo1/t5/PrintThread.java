package tpo1.t5;

class PrintThread implements Runnable {
    private String symbol;
    private Object lock;

    public PrintThread(String symbol, Object lock) {
        this.symbol = symbol;
        this.lock = lock;
    }

    public void run() {
    for (int i = 0; i < 100; i++) {
        synchronized (lock) {
            System.out.println(symbol);
            lock.notify();
            try {
                if (i < 99) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
}
