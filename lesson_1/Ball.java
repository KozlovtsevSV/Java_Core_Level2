package ru.geekbrains;

import java.awt.*;
import java.util.Random;

public class Ball extends Sprite {
    Random rnd = new Random();
    private final Color color;
    private float vX;
    private float vY;

    Ball() {
        halfHeight = 20 + (float) (Math.random() * 50f);
        halfWidth = halfHeight;
        color = new Color(rnd.nextInt());
        vX = (float) (100f + (Math.random() * 200f));
        vY = (float) (100f + (Math.random() * 200f));
    }

    // добавим конструктор с произвольными координатами появления мячика
    Ball(int x, int y) {
        this.x = x;
        this.y = y;
        halfHeight = 20 + (float) (Math.random() * 50f);
        halfWidth = halfHeight;
        color = new Color(rnd.nextInt());
        // сделаем так что начальное направление движения добавленого мяча будет произвольным
        if(rnd.nextBoolean()){
            vX = (float) (100f + (Math.random() * 200f));
        }
        else{
            vX = -(float) (100f + (Math.random() * 200f));
        }

        if(rnd.nextBoolean()){
            vY = (float) (100f + (Math.random() * 200f));
        }
        else{
            vY = -(float) (100f + (Math.random() * 200f));
        }

    }

    @Override
    void update(GameCanvas canvas, float deltaTime) {
        x += vX * deltaTime;
        y += vY * deltaTime;

        if (getLeft() < canvas.getLeft()) {
            setLeft(canvas.getLeft());
            vX = -vX;
        }
        if (getRight() > canvas.getRight()) {
            setRight(canvas.getRight());
            vX = -vX;
        }
        if (getTop() < canvas.getTop()) {
            setTop(canvas.getTop());
            vY = -vY;
        }
        if (getBottom() > canvas.getBottom()) {
            setBottom(canvas.getBottom());
            vY = -vY;
        }
    }

    @Override
    void render(GameCanvas canvas, Graphics g) {
        g.setColor(color);
        g.fillOval((int) getLeft(), (int) getTop(),
                (int) getWidth(), (int) getHeight());
    }
}