package tpo1.t3;

public class BallThread extends Thread {
    private final Ball b;

    public BallThread(Ball ball){
        b = ball;
        if (b.isRed) {
            setPriority(Thread.MAX_PRIORITY);
        } else {
            setPriority(Thread.MIN_PRIORITY);
        }
    }
    @Override
    public void run(){
        try{
            for(int i=1; i<10000; i++){
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);

            }
        } catch(InterruptedException ex){
            System.out.println("Interruption");
        }
    }
}