package ru.geekbrains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainCircles extends JFrame {

    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static int countSprite = 1;
    private static Boolean flagDrawFrame = true;

    Sprite[] sprites = new Sprite[1];

    public static void main(String[] args) {
        new MainCircles();
    }

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Circles");
        GameCanvas canvas = new GameCanvas(this);
        initApplication();
        add(canvas);
        setVisible(true);

        // добавим обрабочик события для канвы
        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                // останавливаем движения мячиков пока не проверим попадание в шарик
                flagDrawFrame = false;
                //так как большинство мышек имеют минимум 3 кнопки будем считать что мышь 3х и выше кнопок
                // тогда #1 -это ЛКМ
                // #2 - колесико мыши
                // #3 - это ПКМ
                // если нажата правая кнопка мыши (#3) будем добавлять Ball
                // на случай появления ошибок добавим обработку исключения чтобы движение мячиков не прерывалось даже после ошибки
                try {
                    if(e.getButton() == 3){
                        addSprite(e.getX(), e.getY());
                    }
                    // если нажата левая кнопка мыши (#1) будем удалять Ball
                    else if(e.getButton() == 1){
                        deleteSprite(e.getX(), e.getY());
                    }
                }
                finally {
                    flagDrawFrame = true;
                }

            }
        });

    }

    private void addSprite(int x, int y){
        // ограничем количество мячиков для того чтобы не выйти за пределы int и не перегружать процесор перерисовкой огромного количества мячико
        // 10000 вполне достаточно
        if(sprites.length >= 10000){
            return;
        }

        if (countSprite == sprites.length) {
            Sprite[] spritesAdd = new Sprite[sprites.length * 2];
            for (int i = 0; i < countSprite; i++) {
                spritesAdd[i] = sprites[i];
            }
            sprites = spritesAdd;
        }
        sprites[countSprite ++] = new Ball(x, y);

    }

    private void deleteSprite(int x, int y){

        // если массив уже равен 0 убирать шарики уже нет смысла так как их нет
        if(countSprite == 0){
            return;
        }

        // будем не просто удалять, а удалять если попали по кружочку
        // делаем обратный цикл так как последний мячик находится на самом верхнем слое т.е. перерисовывается последним и перекрывает все остальные
        for (int i = countSprite - 1; i >= 0 ; i--) {
            if(checkClickBoll(i, x, y)){
                // если попали по мячику удаляем этот мячик с массива
                int delta = 0;
                for (int j = 0; j < countSprite; j++) {
                    // прокускаем мячик так как его необходимо удалить
                    if (j == i){delta ++; continue;}
                    sprites[j - delta] = sprites[j];
                }
                // если попали в мячик дальше нет смысла искать попадания
                countSprite --;
                break;
            }
        }
    }

    private Boolean checkClickBoll(int indexSprite, int x, int y){
        // проверка попадания в окружность восмользуемся теоремой Пифагора
        return Math.pow(x - sprites[indexSprite].x, 2) + Math.pow(y - sprites[indexSprite].y, 2) <=  Math.pow(sprites[indexSprite].halfWidth, 2);
    }

    private void initApplication() {
        for (int i = 0; i < countSprite; i++) {
            sprites[i] = new Ball();
        }
    }

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        if(flagDrawFrame){
            Background.setColorBackground(canvas, deltaTime);
            update(canvas, deltaTime);
            render(canvas, g);
        }
    }

    private void update(GameCanvas canvas, float deltaTime) {
        for (int i = 0; i < countSprite; i++) {
            sprites[i].update(canvas, deltaTime);
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < countSprite; i++) {
            sprites[i].render(canvas, g);
        }
    }

}