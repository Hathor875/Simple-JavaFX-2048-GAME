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

public class GameBoardOperation {



     private  int[] moveAndMergeLeft(int[] row) {
        int[] newRow = new int[row.length];
        int newIndex = 0;

        for (int j : row) {
            if (j != 0) {
                if (newRow[newIndex] == 0) {
                    newRow[newIndex] = j;
                } else if (newRow[newIndex] == j) {
                    Score.score += newRow[newIndex];
                    Score.scoreText.setText("Score: " + Score.score);
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

    private int[] moveAndMergeRight(int[] row) {
        int[] newRow = new int[row.length];
        int newIndex = row.length - 1;

        for (int i = row.length - 1; i >= 0; i--) {
            if (row[i] != 0) {
                if (newRow[newIndex] == 0) {
                    newRow[newIndex] = row[i];
                } else if (newRow[newIndex] == row[i]) {
                    newRow[newIndex] *= 2;
                    Score.score += newRow[newIndex];
                    Score.scoreText.setText("Score: " + Score.score);
                    newIndex--;
                } else {
                    newIndex--;
                    newRow[newIndex] = row[i];
                }
            }
        }
        return newRow;
    }


    private int [][] moveUpOrDownAndMarge(GameBoard.Direction moveDirection, int[][] currentMatrix, int matrixSize){
        int[][] tempMatrix = new int[matrixSize][matrixSize];
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
        return  tempMatrix;
    }


    //todo refactor in progress
    private int [][] moveRightOrLeftAndMarge(GameBoard.Direction moveDirection, int[][] currentMatrix, int matrixSize){
        int[][] tempMatrix = new int[matrixSize][matrixSize];
            for (int i = 0; i < matrixSize; i++) {
                int[] row = currentMatrix[i].clone();

                if (moveDirection == GameBoard.Direction.LEFT) {
                    row = moveAndMergeLeft(row);
                } else {
                    row = moveAndMergeRight(row);
                }
                tempMatrix[i] = row;
            }
        return  tempMatrix;
    }
        
    
    void moveAllTitles(GameBoard.Direction moveDirection) {
        int[][] currentMatrix = GameMatrix.getInstance().getGameMatrix();
        int matrixSize = GameMatrix.getInstance().getMatrixSize();
        int[][] tempMatrix;
        switch (moveDirection) {
            case LEFT, RIGHT -> tempMatrix = moveRightOrLeftAndMarge(moveDirection,currentMatrix,matrixSize);
            case UP, DOWN -> tempMatrix = moveUpOrDownAndMarge(moveDirection,currentMatrix,matrixSize);
            default -> throw new IllegalStateException("Unexpected value: " + moveDirection);
        }
        GameMatrix.getInstance().setGameMatrix(tempMatrix);
    }


    public  List<GameMatrix.Point> findEmptyFields(int[][] matrix) {
        return IntStream.range(0, matrix.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, matrix[i].length)
                        .filter(j -> matrix[i][j] == 0)
                        .mapToObj(j -> new GameMatrix.Point(i, j)))
                .collect(Collectors.toList());
    }

    public void addRandomTitle() {
        List<GameMatrix.Point> tempFreeTitle = findEmptyFields(GameMatrix.getInstance().getGameMatrix());
        if(tempFreeTitle.isEmpty()){
            GameRenderer gameRenderer = new GameRenderer();
            gameRenderer.renderGameMatrix();
        }
        else {
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
    }
    StackPane addRectangleWithText(int number) {
        double sizeY = 80;
        double sizeX = 80;
        if (number == 0) {
            Rectangle rectangle = new Rectangle(sizeX,sizeY, Color.web("#edebe6"));
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rectangle);
            return stackPane;
        } else {
            Color tileColor = TileColors.getColorForValue(number);
            Rectangle rectangle = new Rectangle(sizeX, sizeY, tileColor);
            Text text = new Text(String.valueOf(number));
            text.setFont(font("Verdana", FontWeight.BOLD, 30));
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rectangle, text);
            return stackPane;
        }
    }
}
