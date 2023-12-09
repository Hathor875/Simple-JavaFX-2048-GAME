package com.example.simple2048game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class GameRenderer extends Application {
   public static GridPane gameArea;
   public static BorderPane mainArea;
   public static StackPane score;
   private static boolean winFlag = false;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("2048 GAME");
        gameArea = new GridPane();
        mainArea = new BorderPane();
        score = Score.renderScore();
        mainArea.setTop(score);
        score.setBackground(Background.fill(Color.BISQUE));
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        gameArea.setHgap(screenHeight/1000);
        gameArea.setVgap(screenWidth/1000);
        mainArea.setCenter(gameArea);
        gameArea.setPrefSize(Screen.getPrimary().getBounds().getHeight()/4,Screen.getPrimary().getBounds().getHeight()/4);
        gameArea.setStyle("-fx-background-color: #e0ded7; -fx-border-color: black;");
        primaryStage.getIcons().add(new Image("/icon2048.jpg"));
        Scene scene = new Scene(mainArea);
            
            
        primaryStage.setScene(scene);
        GameBoardOperation gameBoardOperation = new GameBoardOperation();
        for (int i = 0; i < 2; i++) {
            gameBoardOperation.addRandomTitle();
        }

        renderGameMatrix();
        gameArea.setOnKeyPressed(event -> {
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
            if (checkLose()) {
                gameBoardOperation.addRandomTitle();
            }
            renderGameMatrix();
        });
        primaryStage.show();
        gameArea.requestFocus();
    }
    public void renderGameMatrix() {
        int[][] tab = GameMatrix.getInstance().getGameMatrix();
           if (!winFlag) {
               checkWin();
           }
        if (checkLose()){
            for (int i = 0; i < GameMatrix.getInstance().getMatrixSize(); i++) {
            for (int k = 0; k < GameMatrix.getInstance().getMatrixSize(); k++) {
                gameArea.add(new GameBoardOperation().addRectangleWithText(tab[k][i]), i, k);
            }
        }}
    }
    static private void clearGameMatrix() {
        gameArea.getChildren().clear();
    }
    static private boolean checkLose(){
        return null != new GameBoardOperation().findEmptyFields(GameMatrix.getInstance().getGameMatrix());
    }

    static private void checkWin(){
        int[][] gameMatrix = GameMatrix.getInstance().getGameMatrix();
        int elementToFind = 2048;


        for (int[] row : gameMatrix) {
            for (int element : row) {
                if (element == elementToFind) {
                    winFlag = true;
                    message("Win!","Continue playing","Game-over");
                    break;
                }
            }

        }

    }


    static public void message(String messageText, String buttonText,String buttonText2){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ButtonType okButton = new ButtonType(buttonText);
        ButtonType resetButton = new ButtonType(buttonText2);
        alert.getButtonTypes().setAll(okButton,resetButton);
        alert.setTitle(messageText);
        alert.setHeaderText(null);
        Text message = new javafx.scene.text.Text(messageText+"\n your score: " + Score.getScore()+"  ");
        message.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 16));
        message.setFill(Color.CRIMSON);
        alert.getDialogPane().setContent(message);
        alert.showAndWait().ifPresent(response -> {
            if (response == resetButton) {

               GameRenderer gameRenderer = new GameRenderer();
               gameRenderer.gameOver();
            }



        });
    }


     public void gameOver(){
        clearGameMatrix();
        Alert lose = new Alert(Alert.AlertType.INFORMATION);
        ButtonType okButton = new ButtonType("Restart");
        lose.getButtonTypes().setAll(okButton);
        lose.setTitle("Lose");
        lose.setHeaderText(null);
        Text loseMessage = new javafx.scene.text.Text("Lose\n your score: " + Score.getScore()+"  ");
        loseMessage.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 16));
        loseMessage.setFill(Color.CRIMSON);
        lose.getDialogPane().setContent(loseMessage);
        lose.showAndWait().ifPresent(response -> {
        });
        Score.setScore(0);

        GameMatrix.getInstance().setGameMatrix(new int [GameMatrix.getInstance().getMatrixSize()][GameMatrix.getInstance().getMatrixSize()]);
        GameBoardOperation gameBoardOperation = new GameBoardOperation();
        winFlag = false;
        for (int i = 0; i < 2; i++) {
            gameBoardOperation.addRandomTitle();
        }
        renderGameMatrix();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

