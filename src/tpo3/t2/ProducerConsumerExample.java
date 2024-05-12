package tpo3.t2;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        Drop drop = new Drop();
        (new Thread(new Producer(drop, 100))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
