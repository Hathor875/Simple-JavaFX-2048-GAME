package com.example.simple2048game;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public final class TileColors {
    private static final Map<Integer, Color> colors = new HashMap<>();
    static {
        /* 0 to 2048 is a title number in 2048 game */
        colors.put(0, Color.web("#f2e6ff"));
        colors.put(2, Color.web("#ffcccb"));
        colors.put(4, Color.web("#ffeb99"));
        colors.put(8, Color.web("#99e6e6"));
        colors.put(16, Color.web("#99ff99"));
        colors.put(32, Color.web("#80ff1a"));
        colors.put(64, Color.web("#4da6ff"));
        colors.put(128, Color.web("#ff80b3"));
        colors.put(256, Color.web("#ff9933"));
        colors.put(512, Color.web("#cc99ff"));
        colors.put(1024, Color.web("#00cc99"));
        colors.put(2048, Color.web("#ff3300"));
    }
    public static Color getColorForValue(int value) {
        return colors.getOrDefault(value, Color.web("#fcf2fc")); //
    }
}