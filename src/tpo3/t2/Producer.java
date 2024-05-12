package tpo3.t2;
import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int size;

    public Producer(Drop drop, int size) {
        this.drop = drop;
        this.size = size;
    }

    public void run() {
        int[] importantInfo = new int[size];
        for (int i = 0; i < importantInfo.length; i++) {
            importantInfo[i] = i + 1;
        }

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        drop.put(0);
    }
}