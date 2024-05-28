package tpo2;

public class Result {
    private int[][] resultMatrix;

    public Result(int [][] matrix) {
        this.resultMatrix = matrix;
    }

    public void printMatrix() {
        for (int[] row : this.resultMatrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    public int[][] getMatrix() {
        return resultMatrix;
    }
}