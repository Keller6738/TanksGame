package com.example.tanksgame;

public class Obstacle {
    private final Color kColor;
    private final int x, y;
    private final boolean explodable;

    public Obstacle(Color color, int x, int y, boolean explodable) {
        this.kColor = color;
        this.x = x;
        this.y = y;
        this.explodable = explodable;
    }
}
