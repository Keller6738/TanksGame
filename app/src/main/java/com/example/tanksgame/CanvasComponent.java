package com.example.tanksgame;

public abstract class CanvasComponent {
    protected final Color kColor;
    protected double x, y;
    protected int angle;
    protected double TIME = 0.05;

    public CanvasComponent(Color kColor, double x, double y, int angle) {
        this.kColor = kColor;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Color getColor() {
        return kColor;
    }

    public void move() {
        this.x += 2 * Math.cos(angle);
        this.y += 2 * Math.sin(angle);
    }

    public void turn() {
        this.angle += 1;
    }

    abstract void dutyCycle();
    abstract void draw();
}
