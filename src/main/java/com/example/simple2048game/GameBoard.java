package com.example.simple2048game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import static javafx.scene.text.Font.font;

public class GameBoard {
   enum direction {
        UP,DOWN,LEFT,RIGHT
    }


     static void MoveAllTitle(direction moveDirection) {
       int[][] tempTAB =  GameMatrix.getInstance().getGameMatrix();
       tempTAB[1][1]=4;



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
    }

    public static StackPane AddRectangleWithText(int number) {
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

    static final double PANE_SIZE = 100 * GameMatrix.getInstance().getMatrixSize();
    private static final double RECTANGLE_SIZE = PANE_SIZE / GameMatrix.getInstance().getMatrixSize();
}
