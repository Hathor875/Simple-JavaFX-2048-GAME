package com.example.simple2048game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javafx.scene.text.Font.font;

public class GameBoard {
  

    enum direction {
        UP, DOWN, LEFT, RIGHT
    }





    public static final class GameMatrix {

        private static int[] moveAndMergeLeft(int[] row) {
            int[] newRow = new int[row.length];
            int newIndex = 0;

            for (int i = 0; i < row.length; i++) {
                if (row[i] != 0) {  // Jeśli kafelek nie jest pusty
                    if (newRow[newIndex] == 0) {  // Jeśli docelowe miejsce jest puste
                        newRow[newIndex] = row[i];
                    } else if (newRow[newIndex] == row[i]) {  // Jeśli kafelki mają tę samą wartość
                        newRow[newIndex] *= 2;  // Łącz kafelki
                        newIndex++;  // Przesuń wskaźnik na następne miejsce
                    } else {
                        newIndex++;  // Przesuń wskaźnik na następne miejsce
                        newRow[newIndex] = row[i];  // Umieść kafelek
                    }
                }
            }
            return newRow;
        }


        private static int[] moveAndMergeRight(int[] row) {
            int[] newRow = new int[row.length];
            int newIndex = row.length - 1;

            for (int i = row.length - 1; i >= 0; i--) {
                if (row[i] != 0) {
                    if (newRow[newIndex] == 0) {
                        newRow[newIndex] = row[i];
                    } else if (newRow[newIndex] == row[i]) {
                        newRow[newIndex] *= 2;
                        newIndex--;
                    } else {
                        newIndex--;
                        newRow[newIndex] = row[i];
                    }
                }
            }
            return newRow;
        }

        private static int[] moveAndMergeUp(int[] col) {
            return moveAndMergeLeft(col);
        }

        private static int[] moveAndMergeDown(int[] col) {
            return moveAndMergeRight(col);
        }

        static void MoveAllTitle(direction moveDirection) {


            int[][] currentMatrix = GameMatrix.getInstance().gameMatrix;
            int matrixSize = GameMatrix.getInstance().getMatrixSize();

            int[][] tempMatrix = new int[matrixSize][matrixSize];


            switch (moveDirection) {
                case LEFT, RIGHT -> {
                    for (int i = 0; i < matrixSize; i++) {
                        int[] row = currentMatrix[i].clone();

                        if (moveDirection == direction.LEFT) {
                            row = moveAndMergeLeft(row);
                        } else {
                            row = moveAndMergeRight(row);
                        }

                        tempMatrix[i] = row;
                    }
                }
                case UP, DOWN -> {
                    for (int j = 0; j < matrixSize; j++) {
                        int[] col = new int[matrixSize];
                        for (int i = 0; i < matrixSize; i++) {
                            col[i] = currentMatrix[i][j];
                        }

                        if (moveDirection == direction.UP) {
                            col = moveAndMergeLeft(col);
                        } else {
                            col = moveAndMergeRight(col);
                        }

                        for (int i = 0; i < matrixSize; i++) {
                            tempMatrix[i][j] = col[i];
                        }
                    }
                }
            }


            GameMatrix.getInstance().gameMatrix = tempMatrix;
        }
        private final int matrixSize = 4;
        private int[][] gameMatrix;
        private static volatile GameMatrix INSTANCE;

        public int getMatrixSize() {
            return matrixSize;
        }

        public int[][] getGameMatrix() {
            return gameMatrix;
        }


        public  GameMatrix() {
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

        public   List<Point> findEmptyFields(int[][] matrix) {
            return IntStream.range(0, matrix.length)
                    .boxed()
                    .flatMap(i -> IntStream.range(0, matrix[i].length)
                            .filter(j -> matrix[i][j] == 0)
                            .mapToObj(j -> new Point(i, j)))
                    .collect(Collectors.toList());
        }
        public static class Point {
            int x, y;

            Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        public void addRandomTitle() {


            List<Point> tempFreeTitle = findEmptyFields(GameMatrix.getInstance().getGameMatrix());
            int rnd = (int) (Math.random() * tempFreeTitle.size());

            int n = (int) (Math.random() * 4 + 1);
            int value;
            if (n <= 3) {
                value = 2;
            } else {
                value = 4;
            }
            GameMatrix.getInstance().gameMatrix[tempFreeTitle.get(rnd).x][tempFreeTitle.get(rnd).y] = value;
        }

      static StackPane AddRectangleWithText(int number) { //todo add color change for value
            if (number == 0) {
                Rectangle rectangle = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE, Color.web("#edebe6"));
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rectangle);
                return stackPane;
            } else {
                Rectangle rectangle = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE, Color.BISQUE);
                Text text = new Text(String.valueOf(number));
                text.setFont(font("Verdana", FontWeight.BOLD, 20));
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rectangle, text);
                return stackPane;
            }

        }


    }  public static final double PANE_SIZE = 100 * GameMatrix.getInstance().getMatrixSize();
    private static final double RECTANGLE_SIZE = PANE_SIZE / GameMatrix.getInstance().getMatrixSize();
}