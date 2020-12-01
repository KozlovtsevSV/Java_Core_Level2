package ru.geekbrains;

import java.awt.*;

public abstract class Background {
    static Color color = Color.WHITE;
    private static final float AMPLITUDE = 255f / 2f;
    private static int rColor = 0;
    private static int gColor = 0;
    private static int bColor = 0;
    private static float time = 0;

    static void setColorBackground(GameCanvas canvas, float deltaTime){
        color = getNextColor(deltaTime);
        //System.out.println(color);
        canvas.setBackground(color);
    }

    private static Color getNextColor(float deltaTime){
        time += deltaTime;
        rColor = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(time * 2f));
        gColor = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(time * 3f));
        bColor = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(time));
        return color = new Color(rColor, gColor, bColor);
    }
}
