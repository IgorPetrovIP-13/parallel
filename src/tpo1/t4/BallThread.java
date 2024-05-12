package tpo1.t4;

public class BallThread extends Thread {
    private final Ball b;
    private final BallThread prevThread;

    public BallThread(Ball ball, BallThread previousThread){
        b = ball;
        this.prevThread = previousThread;
    }

    @Override
    public void run(){
        try {
            if (prevThread != null) {
                prevThread.join();
            }
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 1; i < 10000; i++) {
                    b.move();
                    System.out.println("Thread name = " + Thread.currentThread().getName());
                    Thread.sleep(5);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Interruption of " + Thread.currentThread().getName());
        }
    }

    public synchronized void stopRunning() {
        interrupt();
    }
}