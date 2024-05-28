package tpo2.Fox;
import tpo2.IMultiplier;

public class FoxMul implements IMultiplier {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] result;
    private int threadNum;

    public FoxMul(int[][] matrixA, int[][] matrixB, int threadNum) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.threadNum = threadNum;
    }

    public void setNewThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    @Override
    public void setNewMatrices(int[][] A, int[][] B) {
        this.matrixA = A;
        this.matrixB = B;
    }

    @Override
    public int[][] multiply() {
        int n = this.matrixA.length;
        int[][] result = new int[n][n];
        int blockSize = (int) Math.ceil((double) n / this.threadNum);
        Thread[] threads = new Thread[this.threadNum];

        for (int threadId = 0; threadId < this.threadNum; threadId++) {
            int finalThreadId = threadId;
            threads[threadId] = new Thread(() -> {
                for (int block = 0; block < this.threadNum; block++) {
                    int startRow = finalThreadId * blockSize;
                    int endRow = Math.min((finalThreadId + 1) * blockSize, n);

                    for (int i = startRow; i < endRow; i++) {
                        for (int j = 0; j < n; j++) {
                            int sum = 0;
                            for (int k = block * blockSize; k < Math.min((block + 1) * blockSize, n); k++) {
                                sum += this.matrixA[i][k] * this.matrixB[k][j];
                            }
                            int currentValue = result[i][j];
                            result[i][j] = currentValue + sum;
                        }
                    }
                }
            });
            threads[threadId].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}