package tpo2;

import tpo2.Default.DefaultMul;
import tpo2.Default.ParallelMul;
import tpo2.Fox.FoxMul;
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

    public static boolean areMatricesEqual(int[][] matrix1, int[][] matrix2, int[][] matrix3) {
        if (matrix1 == null || matrix2 == null || matrix3 == null) {
            return false;
        }

        if (matrix1.length != matrix2.length || matrix1.length != matrix3.length) {
            return false;
        }

        for (int i = 0; i < matrix1.length; i++) {
            if (matrix1[i].length != matrix2[i].length || matrix1[i].length != matrix3[i].length) {
                return false;
            }

            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] != matrix2[i][j] || matrix1[i][j] != matrix3[i][j]) {
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
        int[][] matrix1 = generateSquareMatrix(500);
        int[][] matrix2 = generateSquareMatrix(500);

        IMultiplier defaultMultiplier = new DefaultMul(matrix1, matrix2);
        IMultiplier defaultParallelMultiplier = new ParallelMul(matrix1, matrix2, 4);
        IMultiplier foxMultiplier = new FoxMul(matrix1, matrix2, 4);

        Result res1 = new Result(measureExecutionTime(defaultMultiplier));
        Result res2 = new Result(measureExecutionTime(defaultParallelMultiplier));
        Result res3 = new Result(measureExecutionTime(foxMultiplier));

        System.out.println("Are matrices equal: " + areMatricesEqual(res1.getMatrix(), res2.getMatrix(), res3.getMatrix()));
    }
}
