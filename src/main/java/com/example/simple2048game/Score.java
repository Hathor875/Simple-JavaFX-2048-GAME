package com.example.simple2048game;

public class Score {
    private static int score = 0 ;
    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Score.score = score;
    }

    public static void addToScore(int addValue){
       score += addValue;
   }
}
