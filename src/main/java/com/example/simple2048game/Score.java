package com.example.simple2048game;

import javafx.scene.layout.StackPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.scene.text.Font.font;


public class Score {
    static int score = 0;
    public static Text scoreText = new Text("Score: " + score);
    private static final StackPane scoreContainer = new StackPane(scoreText);

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Score.score = score;
        updateScoreText();
    }

    private static void updateScoreText() {
        scoreText.setText("Score:  " + score);
    }

    public static StackPane renderScore() {
        scoreText.setText("Score: " + score);
        scoreText.setFont(font("Verdana", FontWeight.BOLD, 16));
        return scoreContainer;
    }
}