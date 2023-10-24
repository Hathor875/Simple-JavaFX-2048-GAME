package com.example.simple2048game;

public final class GameMatrix {


    private final int matrixSize = 4;
    private int[][] gameMatrix;
    private static volatile GameMatrix INSTANCE;

    public int getMatrixSize() {
        return matrixSize;
    }

    public int[][] getGameMatrix() {
        return gameMatrix;
    }

    public void setGameMatrix(int[][] newMatrix) {
        this.gameMatrix = newMatrix;
    }
    private GameMatrix() {
        gameMatrix = new int[matrixSize][matrixSize];

    }
    public static GameMatrix getInstance() {
        if (INSTANCE == null) {
            synchronized (GameMatrix.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GameMatrix();
                }
            }
        }
        return INSTANCE;
    }
    record Point(int x, int y) {
    }
}
