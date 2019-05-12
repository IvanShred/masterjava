package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    // TODO implement parallel multiplication matrixA*matrixB
    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < matrixSize; i++) {
            futures.add(executor.submit(new CalculateRowElements(i, matrixSize, matrixA, matrixB, matrixC)));
        }
        while (!futures.isEmpty()) {
            Iterator<Future> iterator = futures.iterator();
            while (iterator.hasNext()) {
                Future nextFuture = iterator.next();
                if (nextFuture.isDone()) {
                    iterator.remove();
                }
            }
        }
        return matrixC;
    }

    // TODO optimize by https://habrahabr.ru/post/114797/
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        try {
            int thatColumn[] = new int[matrixSize];
            for (int j = 0; ; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    thatColumn[k] = matrixB[k][j];
                }

                for (int i = 0; i < matrixSize; i++) {
                    int thisRow[] = matrixA[i];
                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += thisRow[k] * thatColumn[k];
                    }
                    matrixC[i][j] = sum;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static class CalculateRowElements implements Runnable {
        int row;
        int matrixSize;
        int[][] matrixA;
        int[][] matrixB;
        int[][] matrixC;

        CalculateRowElements(int rowIndex, int matrixSize, int[][] matrixA, int[][] matrixB, int[][] matrixC) {
            this.row = rowIndex;
            this.matrixSize = matrixSize;
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.matrixC = matrixC;
        }

        @Override
        public void run() {
            try {
                int thatColumn[] = new int[matrixSize];
                for (int j = 0; ; j++) {
                    for (int k = 0; k < matrixSize; k++) {
                        thatColumn[k] = matrixB[k][j];
                    }
                    int thisRow[] = matrixA[row];
                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += thisRow[k] * thatColumn[k];
                    }
                    matrixC[row][j] = sum;
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }
}
