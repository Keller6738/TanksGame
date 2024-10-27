package com.example.tanksgame;

public class Rocket extends CanvasComponent {
    public Rocket(Color kColor, double initX, double initY, int angle) {
        super(kColor, initX, initY, angle);
    }

    @Override
    public void dutyCycle() {
        move();
        draw();
    }

    @Override
    public void draw() {
    }
}
