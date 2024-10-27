package com.example.tanksgame;

public class Tank extends CanvasComponent {
    private boolean movable = false;

    public Tank(Color color, double initX, double initY, int initAngle) {
        super(color, initX, initY, initAngle);
    }

    public void toggleMobility() {
        this.movable = !this.movable;
    }

    @Override
    public void dutyCycle() {
        if (!this.movable) turn();
        else move();
        draw();
    }

    @Override
    public void draw() {
    }
}
