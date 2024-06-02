package tpo8.dataOnClient;

public class MatrixMultiplier {
    private Matrix matrixA;
    private Matrix matrixB;
    private int threadNum;

    public MatrixMultiplier(Matrix A, Matrix B, int threadNum) {
        this.matrixA = A;
        this.matrixB = B;
        this.threadNum = threadNum;
    }

    public Matrix multiply() {
        int rows1 = this.matrixA.getRows();
        int cols1 = this.matrixA.getCols();
        int cols2 = this.matrixB.getCols();

        Matrix result = new Matrix(rows1, cols2);

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
                        int localSum = 0;
                        for (int k = 0; k < cols1; k++) {
                            localSum += this.matrixA.getValue(row, k) * this.matrixB.getValue(k, j);
                        }
                        result.setValue(row, j, localSum);
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
