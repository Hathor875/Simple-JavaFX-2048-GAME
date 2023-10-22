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

    //todo move title without checking
    static void MoveAllTitle(direction moveDirection) {
        int[][] tempTAB = GameMatrix.getInstance().getGameMatrix();

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


        public GameMatrix() {
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

        private  List<Point> findEmptyFields(int[][] matrix) {
            return IntStream.range(0, matrix.length)
                    .boxed()
                    .flatMap(i -> IntStream.range(0, matrix[i].length)
                            .filter(j -> matrix[i][j] == 0)
                            .mapToObj(j -> new Point(i, j)))
                    .collect(Collectors.toList());
        }
        public class Point {
            int x, y;

            Point(int x, int y) {
                this.x = x;
                this.y = y;
            }





        }
        public void addRandomTitle() {


            List<Point> tempFreeTitle = findEmptyFields(GameMatrix.getInstance().getGameMatrix());
            int rnd = (int) (Math.random() * GameMatrix.getInstance().matrixSize * 2);

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