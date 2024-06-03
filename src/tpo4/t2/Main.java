package tpo4.t2;
import java.util.Random;

public class Main {

    public static int[][] generateSquareMatrix(int size) {
        int[][] matrix = new int[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(100) + 1;
            }
        }

        return matrix;
    }

    public static boolean areMatricesEqual(int[][] matrix1, int[][] matrix2) {
        if (matrix1 == null || matrix2 == null) {
            return false;
        }

        if (matrix1.length != matrix2.length) {
            return false;
        }

        for (int i = 0; i < matrix1.length; i++) {
            if (matrix1[i].length != matrix2[i].length) {
                return false;
            }

            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int[][] measureExecutionTime(IMultiplier function) {
        long startTime = System.currentTimeMillis();
        int[][] result = function.multiply();
        long endTime = System.currentTimeMillis();
        System.out.println(function.getClass().getName() + " executed in " + (endTime - startTime) + " ms");
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        int[][] matrix1 = generateSquareMatrix(1000);
        int[][] matrix2 = generateSquareMatrix(1000);

        IMultiplier parallelMul = new ParallelMul(matrix1, matrix2, 4);
        IMultiplier forkjoinMul = new ForkJoinMul(matrix1, matrix2);

        Result res1 = new Result(measureExecutionTime(parallelMul));
        Result res2 = new Result(measureExecutionTime(forkjoinMul));

        System.out.println("Are matrices equal: " + areMatricesEqual(res1.getMatrix(), res2.getMatrix()));
    }
}
