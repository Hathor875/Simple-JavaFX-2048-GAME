package com.example.simple2048game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
!Jak najmniej używania metod statycznych
!Metody, które nie są wykorzystywane poza klasą muszą być private
!Wszystkie zmienne które są na całą klasę to na początku dajesz
!Staraj się trzymać, żeby dana klasa dotyczyła tylko jednego elementu, to znaczy jeżeli jest GameRenderer,
 to on tylko renderuje mapę, ale jej nie odpala*/
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
        GameBoardOperation.addRandomTitle();
        GameBoardOperation.addRandomTitle();
        renderGameMatrix();
        area.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP ->
                    GameBoardOperation.moveAllTitle(GameBoard.Direction.UP);
                case DOWN ->
                    GameBoardOperation.moveAllTitle(GameBoard.Direction.DOWN);
                case LEFT ->
                    GameBoardOperation.moveAllTitle(GameBoard.Direction.LEFT);

                case RIGHT ->
                    GameBoardOperation.moveAllTitle(GameBoard.Direction.RIGHT);

                default -> throw new IllegalStateException("Unexpected value: " + event.getCode());
            }
            clearGameMatrix();
            GameBoardOperation.addRandomTitle();
            renderGameMatrix();
        });
        primaryStage.show();
        area.requestFocus();
    }
    public void renderGameMatrix() {
        int[][] tab = GameBoard.GameMatrix.getInstance().getGameMatrix();

        for (int i = 0; i < GameBoard.GameMatrix.getInstance().getMatrixSize(); i++) {
            for (int k = 0; k < GameBoard.GameMatrix.getInstance().getMatrixSize(); k++) {
                area.add(GameBoardOperation.addRectangleWithText(tab[k][i]), i, k);
            }
        }
    }
    static public void clearGameMatrix() {
        area.getChildren().clear();
    }


    static public void gameOver(){
        clearGameMatrix();
        System.out.println("lose");
        //todo add game over
    }

    public static void main(String[] args) {
        launch(args);
    }
}

