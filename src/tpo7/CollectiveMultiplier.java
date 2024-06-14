package tpo7;

import mpi.MPI;

public class CollectiveMultiplier {

    static final int NRA = 1000; /* number of rows in matrix A */
    static final int NCA = 1000; /* number of columns in matrix A */
    static final int NCB = 1000; /* number of columns in matrix B */
    static final int MASTER = 0; /* taskid of first task */

    public static void main(String[] args) {
        int[][] a = new int[NRA][NCA];
        int[][] b = new int[NCA][NCB];
        int[][] c = new int[NRA][NCB];

        MPI.Init(args);

        int numtasks = MPI.COMM_WORLD.Size();
        int taskid = MPI.COMM_WORLD.Rank();

        if (numtasks < 2) {
            System.out.println("Need at least two MPI tasks. Quitting...\n");
            MPI.Finalize();
            System.exit(1);
        }

        long startTime = 0;

        if (taskid == MASTER) {
            System.out.println("mpi_mm has started with " + numtasks + " tasks.");
            for (int i = 0; i < NRA; i++) {
                for (int j = 0; j < NCA; j++) {
                    a[i][j] = 10;
                }
            }
            for (int i = 0; i < NCA; i++) {
                for (int j = 0; j < NCB; j++) {
                    b[i][j] = 10;
                }
            }
            startTime = System.currentTimeMillis();
        }

        for (int i = 0; i < NCA; i++) {
            MPI.COMM_WORLD.Bcast(b[i], 0, NCB, MPI.INT, MASTER);
        }

        int averow = NRA / numtasks;
        int extra = NRA % numtasks;
        int[] sendcounts = new int[numtasks];
        int[] displs = new int[numtasks];

        for (int i = 0; i < numtasks; i++) {
            sendcounts[i] = (i < extra) ? averow + 1 : averow;
            displs[i] = (i == 0) ? 0 : displs[i - 1] + sendcounts[i - 1];
        }

        int localRows = sendcounts[taskid];
        int[][] localA = new int[localRows][NCA];
        int[][] localC = new int[localRows][NCB];

        System.out.println("Sending " + localRows + " rows to task " + taskid );
        MPI.COMM_WORLD.Scatterv(a, 0, sendcounts, displs, MPI.OBJECT, localA, 0, localRows, MPI.OBJECT, MASTER);

        for (int k = 0; k < NCB; k++) {
            for (int i = 0; i < localRows; i++) {
                for (int j = 0; j < NCA; j++) {
                    localC[i][k] += localA[i][j] * b[j][k];
                }
            }
            MPI.COMM_WORLD.Gatherv(localC, 0, localRows, MPI.OBJECT, c, 0, sendcounts, displs, MPI.OBJECT, MASTER);
//        MPI.COMM_WORLD.Allgatherv(localC, 0, localRows, MPI.OBJECT, c, 0, sendcounts, displs, MPI.OBJECT);
        }

        if (taskid == MASTER) {
            System.out.println("Received results from all tasks.");
            long endTime = System.currentTimeMillis();
            System.out.println("Executed in " + (endTime - startTime) + "ms");
            System.out.println("Is correct: " + checkIsCorrect(a, b, c));
        }

        MPI.Finalize();
}

    public static boolean checkIsCorrect(int[][] a, int[][] b, int[][] matrixToCheck) {
        int[][] c = new int[NRA][NCB];
        for (int i = 0; i < NRA; i++) {
            for (int j = 0; j < NCB; j++) {
                c[i][j] = 0;
                for (int k = 0; k < NCA; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                if (c[i][j] != matrixToCheck[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}