package tpo2.Default;

import tpo2.IMultiplier;
public class DefaultMul implements IMultiplier {

    private int [][] matrixA;
    private int [][] matrixB;

    public DefaultMul(int[][] A, int[][] B) {
        this.matrixA = A;
        this.matrixB = B;
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

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += this.matrixA[i][k] * this.matrixB[k][j];
                }
            }
        }

        return result;
    }
}
