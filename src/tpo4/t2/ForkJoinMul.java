package tpo4.t2;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinMul implements IMultiplier {
    private int[][] matrixA;
    private int[][] matrixB;

    public ForkJoinMul(int[][] A, int[][] B) {
        this.matrixA = A;
        this.matrixB = B;
    }

    @Override
    public int[][] multiply() {
        int rows1 = this.matrixA.length;
        int cols2 = this.matrixB[0].length;

        int[][] result = new int[rows1][cols2];

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MatrixMultiplyTask(this.matrixA, this.matrixB, result, 0, rows1, cols2));
        pool.shutdown();
        return result;
    }

    private static class MatrixMultiplyTask extends RecursiveTask<Void> {
        private static final int THRESHOLD = 10;
        private int[][] matrixA;
        private int[][] matrixB;
        private int[][] result;
        private int startRow;
        private int endRow;
        private int cols2;

        public MatrixMultiplyTask(int[][] matrixA, int[][] matrixB, int[][] result, int startRow, int endRow, int cols2) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.result = result;
            this.startRow = startRow;
            this.endRow = endRow;
            this.cols2 = cols2;
        }

        @Override
        protected Void compute() {
            int rows = endRow - startRow;
            if (rows <= THRESHOLD) {
                for (int row = startRow; row < endRow; row++) {
                    for (int j = 0; j < cols2; j++) {
                        for (int k = 0; k < matrixA[0].length; k++) {
                            result[row][j] += matrixA[row][k] * matrixB[k][j];
                        }
                    }
                }
            } else {
                int midRow = (startRow + endRow) / 2;
                MatrixMultiplyTask task1 = new MatrixMultiplyTask(matrixA, matrixB, result, startRow, midRow, cols2);
                MatrixMultiplyTask task2 = new MatrixMultiplyTask(matrixA, matrixB, result, midRow, endRow, cols2);
                invokeAll(task1, task2);
            }
            return null;
        }
    }
}