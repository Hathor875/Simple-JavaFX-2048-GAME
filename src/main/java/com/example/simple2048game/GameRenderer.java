package com.example.simple2048game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
!Jak najmniej używania metod statycznych
!Metody, które nie są wykorzystywane poza klasą muszą być private
!Wszystkie zmienne które są na całą klasę to na początku dajesz
!Staraj się t
rzymać, żeby dana klasa dotyczyła tylko jednego elementu, to znaczy jeżeli jest GameRenderer,
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
        System.out.println("lose");
        //todo add game over
    }

    public static void main(String[] args) {
        launch(args);
    }
}

