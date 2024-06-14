package tpo8.dataOnClient;

import java.io.*;
import java.net.*;

public class MatrixClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT_NUMBER = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT_NUMBER);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Client data app running...");

            long startTime = System.currentTimeMillis();

            int size = 1000;
            int threadNum = 5;

            Matrix matrixA = new Matrix(size, size);
            Matrix matrixB = new Matrix(size, size);

            matrixA.fillRandom();
            matrixB.fillRandom();

            out.writeObject(matrixA);
            out.writeObject(matrixB);
            out.writeInt(threadNum);
            out.flush();

            Matrix result = (Matrix) in.readObject();

            long endTime = System.currentTimeMillis();

            System.out.println("Got result matrix with size: " + result.getRows() + "x" + result.getCols());
            System.out.println("Execution time: " + (endTime - startTime) + " ms");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}