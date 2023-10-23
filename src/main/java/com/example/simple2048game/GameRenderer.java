package com.example.simple2048game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class GameRenderer extends Application {
    private static GridPane area;
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("2048 GAME");
        area = new GridPane();
        area.setHgap(3);
        area.setVgap(3);
        area.setPrefSize(GameBoard.PANE_SIZE, GameBoard.PANE_SIZE);
        area.setStyle("-fx-background-color: #e0ded7; -fx-border-color: black;");
        Scene scene = new Scene(area);
        primaryStage.setScene(scene);
        boolean startFlag = true;
        if(startFlag){
            GameBoard.GameMatrix.getInstance().addRandomTitle();
            startFlag = false;
        }


        GameBoard.GameMatrix.getInstance().addRandomTitle();
        renderGameMatrix();
        area.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    GameBoard.GameMatrix.MoveAllTitle(GameBoard.direction.UP);
                    System.out.println("up");
                }
                case DOWN -> {
                    GameBoard.GameMatrix.MoveAllTitle(GameBoard.direction.DOWN);
                    System.out.println("down");
                }
                case LEFT -> {
                    GameBoard.GameMatrix.MoveAllTitle(GameBoard.direction.LEFT);
                    System.out.println("left");
                }
                case RIGHT -> {
                    GameBoard.GameMatrix.MoveAllTitle(GameBoard.direction.RIGHT);
                    System.out.println("right");
                }
            }

            clearGameMatrix();
            GameBoard.GameMatrix.getInstance().addRandomTitle();
            renderGameMatrix();
        });
        primaryStage.show();
        area.requestFocus();
    }
    public void renderGameMatrix() {
        int[][] tab = GameBoard.GameMatrix.getInstance().getGameMatrix();

        for (int i = 0; i < GameBoard.GameMatrix.getInstance().getMatrixSize(); i++) {
            for (int k = 0; k < GameBoard.GameMatrix.getInstance().getMatrixSize(); k++) {
                area.add(GameBoard.GameMatrix.AddRectangleWithText(tab[k][i]), i, k);
            }
        }
        }

   static public void clearGameMatrix() {
        area.getChildren().clear();
    }


    public static void gameOver(){
        clearGameMatrix();
        System.out.println("lose");

        //todo add game over
    }

    public static void main(String[] args) {
        launch(args);
    }
}

