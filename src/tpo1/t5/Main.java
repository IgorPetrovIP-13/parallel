package tpo1.t5;

public class Main {
    public static void main(String[] args) {
        Object lock = new Object();

        Thread thread1 = new Thread(new PrintThread("-", lock));
        Thread thread2 = new Thread(new PrintThread("|", lock));

        thread1.start();
        thread2.start();
    }
}
