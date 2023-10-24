package com.example.simple2048game;

import static javafx.scene.text.Font.font;

public class GameBoard {
     static double PANE_SIZE = 100 * GameMatrix.getInstance().getMatrixSize();
      static final double RECTANGLE_SIZE = PANE_SIZE / GameMatrix.getInstance().getMatrixSize();

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    public static final class GameMatrix {


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
            Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }

}