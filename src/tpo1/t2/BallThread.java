package tpo1.t2;

public class BallThread extends Thread {
    private final Ball b;

    public BallThread(Ball ball){
        b = ball;
    }

    @Override
    public void run(){
        try {
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