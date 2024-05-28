package tpo1.t5;

public class Main {
    public static void main(String[] args) {
        Object lock = new Object();
        Thread thread1 = new Thread(new PrintThread("-", lock), "-");
        Thread thread2 = new Thread(new PrintThread("|", lock), "|");

        thread1.setName("-");
        thread2.setName("|");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}
