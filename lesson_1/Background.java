package ru.geekbrains;

import java.awt.*;
import java.util.Random;

public abstract class Background {
    static Color color = Color.WHITE;
    static Random rnd = new Random();
    static int rColor = 0;
    static int gColor = 0;
    static int bColor = 0;

    static void setColorBackground(GameCanvas canvas){
        color = getNextColor();
        System.out.println(color);
        canvas.setBackground(color);
    }

    private static Color getNextColor(){
        // не могу придумать что то хорошее с цветом фона
        rColor += rnd.nextInt(10);
        if (rColor >= 150){
            rColor = 150;
        }
        gColor += rnd.nextInt(2);
        if (gColor >= 255) {
            gColor = 255;
            bColor += rnd.nextInt(10);
        }
        if (bColor >= 255) {
            rColor = 0;
            gColor = 0;
            bColor = 0;
        }

        return color = new Color(rColor, gColor, bColor);

    }
}
