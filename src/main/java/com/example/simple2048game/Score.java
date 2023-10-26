package com.example.simple2048game;

import javafx.scene.layout.StackPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.scene.text.Font.font;

public class Score {
    static int score = 0;
    public static Text scoreText = new Text("Wynik: " + score);
    private static StackPane scoreContainer = new StackPane(scoreText);

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Score.score = score;
        updateScoreText();
    }

    public static void addToScore(int addValue) {
        score += addValue;
        updateScoreText();
    }

    private static void updateScoreText() {
        scoreText.setText("Wynik: " + score);
    }

    public static StackPane renderScore() {
        scoreText.setText("Wynik: " + score);
        return scoreContainer;  // zwracaj istniejący kontener
    }
}