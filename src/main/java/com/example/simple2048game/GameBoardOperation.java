package com.example.simple2048game;

import com.example.simple2048game.GameBoard.GameMatrix;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javafx.scene.text.Font.font;

public class GameBoardOperation {
    static int[] moveAndMergeLeft(int[] row) {
        int[] newRow = new int[row.length];
        int newIndex = 0;

        for (int j : row) {
            if (j != 0) {
                if (newRow[newIndex] == 0) {
                    newRow[newIndex] = j;
                } else if (newRow[newIndex] == j) {
                    newRow[newIndex] *= 2;
                    newIndex++;
                } else {
                    newIndex++;
                    newRow[newIndex] = j;
                }
            }
        }
        return newRow;
    }

    static int[] moveAndMergeRight(int[] row) {
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

    static void moveAllTitle(GameBoard.Direction moveDirection) {
        int[][] currentMatrix = GameBoard.GameMatrix.getInstance().getGameMatrix();
        int matrixSize = GameBoard.GameMatrix.getInstance().getMatrixSize();
        int[][] tempMatrix = new int[matrixSize][matrixSize];

        switch (moveDirection) {
            case LEFT, RIGHT -> {
                for (int i = 0; i < matrixSize; i++) {
                    int[] row = currentMatrix[i].clone();

                    if (moveDirection == GameBoard.Direction.LEFT) {
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

                    if (moveDirection == GameBoard.Direction.UP) {
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
        GameMatrix.getInstance().setGameMatrix(tempMatrix);

    }


    private static List<GameMatrix.Point> findEmptyFields(int[][] matrix) {
        return IntStream.range(0, matrix.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, matrix[i].length)
                        .filter(j -> matrix[i][j] == 0)
                        .mapToObj(j -> new GameMatrix.Point(i, j)))
                .collect(Collectors.toList());
    }

    public static void addRandomTitle() {
        List<GameMatrix.Point> tempFreeTitle = findEmptyFields(GameMatrix.getInstance().getGameMatrix());
        if(tempFreeTitle.isEmpty()){
            GameRenderer.gameOver();
        }
        int rnd = (int) (Math.random() * tempFreeTitle.size());
        int n = (int) (Math.random() * 10);
        int value;
        if (n <= 8) {
            value = 2;
        } else {
            value = 4;
        }
        GameMatrix.getInstance().getGameMatrix()[tempFreeTitle.get(rnd).x()][tempFreeTitle.get(rnd).y()] = value;
    }

    static StackPane addRectangleWithText(int number) { //todo add color change for value
        if (number == 0) {
            Rectangle rectangle = new Rectangle(GameBoard.RECTANGLE_SIZE, GameBoard.RECTANGLE_SIZE, Color.web("#edebe6"));
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rectangle);
            return stackPane;
        } else {

            Color tileColor = TileColors.getColorForValue(number);
            Rectangle rectangle = new Rectangle(GameBoard.RECTANGLE_SIZE, GameBoard.RECTANGLE_SIZE, tileColor);

            Text text = new Text(String.valueOf(number));
            text.setFont(font("Verdana", FontWeight.BOLD, 20));
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rectangle, text);
            return stackPane;
        }
    }


    public class TileColors {
        private static final Map<Integer, Color> colors = new HashMap<>();

        static {
            colors.put(0, Color.web("#f2e6ff"));
            colors.put(2, Color.web("#ffcccb"));
            colors.put(4, Color.web("#ffeb99"));
            colors.put(8, Color.web("#99e6e6"));
            colors.put(16, Color.web("#99ff99"));
            colors.put(32, Color.web("#80ff1a"));
            colors.put(64, Color.web("#4da6ff"));
            colors.put(128, Color.web("#ff80b3"));
            colors.put(256, Color.web("#ff9933"));
            colors.put(512, Color.web("#cc99ff"));
            colors.put(1024, Color.web("#00cc99"));
            colors.put(2048, Color.web("#ff3300"));


        }

        public static Color getColorForValue(int value) {
            return colors.getOrDefault(value, Color.web("#fcf2fc")); //
        }
    }

}
