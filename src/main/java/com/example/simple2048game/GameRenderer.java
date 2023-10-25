package com.example.simple2048game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;




public class GameRenderer extends Application {
   public static GridPane area;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("2048 GAME");
        area = new GridPane();
        area.setHgap(area.getHeight()/1000);
        area.setVgap(area.getWidth()/1000);

        area.setPrefSize(Screen.getPrimary().getBounds().getHeight()/4,Screen.getPrimary().getBounds().getHeight()/4);
        area.setStyle("-fx-background-color: #e0ded7; -fx-border-color: black;");
        primaryStage.getIcons().add(new Image("/icon2048.jpg"));
        Scene scene = new Scene(area);
        primaryStage.setScene(scene);
        GameBoardOperation gameBoardOperation = new GameBoardOperation();
        for (int i = 0; i < 2; i++) {
            gameBoardOperation.addRandomTitle();
        }

        renderGameMatrix();
        area.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP ->
                     new GameBoardOperation().moveAllTitles(GameBoard.Direction.UP);
                case DOWN ->
                        new GameBoardOperation().moveAllTitles(GameBoard.Direction.DOWN);
                case LEFT ->
                        new GameBoardOperation().moveAllTitles(GameBoard.Direction.LEFT);

                case RIGHT ->
                        new GameBoardOperation().moveAllTitles(GameBoard.Direction.RIGHT);
                default -> throw new IllegalStateException("Unexpected value: " + event.getCode());
            }
            clearGameMatrix();
           gameBoardOperation.addRandomTitle();
            renderGameMatrix();
        });
        primaryStage.show();
        area.requestFocus();
    }
    public void renderGameMatrix() {
        int[][] tab = GameMatrix.getInstance().getGameMatrix();

        for (int i = 0; i < GameMatrix.getInstance().getMatrixSize(); i++) {
            for (int k = 0; k < GameMatrix.getInstance().getMatrixSize(); k++) {
                area.add(new GameBoardOperation().addRectangleWithText(tab[k][i]), i, k);
            }
        }
    }
    static private void clearGameMatrix() {
        area.getChildren().clear();
    }
    static public void gameOver(){

        clearGameMatrix();
        System.out.println("lose"+Score.getScore());
        Score.setScore(0);
        GameMatrix.getInstance().setGameMatrix(new int [GameMatrix.getInstance().getMatrixSize()][GameMatrix.getInstance().getMatrixSize()]);

        //todo add game over
    }

    public static void main(String[] args) {
        launch(args);
    }
}

