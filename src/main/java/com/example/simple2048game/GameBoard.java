package com.example.simple2048game;
public class GameBoard {
     static final double PANE_SIZE = 100 * GameMatrix.getInstance().getMatrixSize();
      static final double RECTANGLE_SIZE = PANE_SIZE / GameMatrix.getInstance().getMatrixSize();

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}