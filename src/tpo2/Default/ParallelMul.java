package tpo2.Default;

import tpo2.IMultiplier;

public class ParallelMul implements IMultiplier {
    private int [][] matrixA;
    private int [][] matrixB;
    private int threadNum;

    public ParallelMul(int[][] A, int[][] B, int threadNum) {
        this.matrixA = A;
        this.matrixB = B;
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
        int rows1 = this.matrixA.length;
        int cols1 = this.matrixA[0].length;
        int cols2 = this.matrixB[0].length;

        int[][] result = new int[rows1][cols2];

        int rowsPerThread = rows1 / this.threadNum;
        int remainingRows = rows1 % this.threadNum;
        int currentRow = 0;

        Thread[] threads = new Thread[this.threadNum];
        for (int i = 0; i < this.threadNum; i++) {
            int rowsForThread = (i < remainingRows) ? rowsPerThread + 1 : rowsPerThread;
            final int startRow = currentRow;
            final int endRow = currentRow + rowsForThread;
            threads[i] = new Thread(() -> {
                for (int row = startRow; row < endRow; row++) {
                    for (int j = 0; j < cols2; j++) {
                        for (int k = 0; k < cols1; k++) {
                            result[row][j] += this.matrixA[row][k] * this.matrixB[k][j];
                        }
                    }
                }
            });
            threads[i].start();
            currentRow += rowsForThread;
        }

        try {
            for (int i = 0; i < this.threadNum; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
