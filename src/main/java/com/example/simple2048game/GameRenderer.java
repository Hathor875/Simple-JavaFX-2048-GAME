package com.example.simple2048game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class GameRenderer extends Application {
    private GridPane area;
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

        renderGameMatrix();
        area.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    GameBoard.MoveAllTitle(GameBoard.direction.UP);
                    System.out.println("up");
                }
                case DOWN -> {
                    GameBoard.MoveAllTitle(GameBoard.direction.DOWN);
                    System.out.println("down");
                }
                case LEFT -> {
                    GameBoard.MoveAllTitle(GameBoard.direction.LEFT);
                    System.out.println("left");
                }
                case RIGHT -> {
                    GameBoard.MoveAllTitle(GameBoard.direction.RIGHT);
                    System.out.println("right");
                }
            }
        });
        primaryStage.show();
        area.requestFocus();
    }
    public void renderGameMatrix() {
        int[][] tab = GameBoard.GameMatrix.getInstance().getGameMatrix();
        GameBoard.GameMatrix.getInstance().addRandomTitle();
        GameBoard.GameMatrix.getInstance().addRandomTitle();
        for (int i = 0; i < GameBoard.GameMatrix.getInstance().getMatrixSize(); i++) {
            for (int k = 0; k < GameBoard.GameMatrix.getInstance().getMatrixSize(); k++) {
                area.add(GameBoard.GameMatrix.AddRectangleWithText(tab[i][k]), i, k);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

