package ru.geekbrains;

import java.util.Arrays;

class MyThread extends Thread{

    private float[] array;
    private int K;

    MyThread(float[]inputArray, int k){

        this.array = inputArray;
        this.K = k;
    }

    public float [] getResult() {
        return array;
    }

    @Override
    public void run() {

        array = arrayProc(array, K);

    }

        public static float [] arrayProc(float [] arrayInput, int k){

          //  float [] result = new float[arrayInput.length];

            for (int i = 0; i < arrayInput.length ; i++) {
                arrayInput[i] = (float)(arrayInput[i] * Math.sin(0.2f + (k + i) / 5) * Math.cos(0.2f + (k + i) / 5) * Math.cos(0.4f + (k + i) / 2));
            }

            return arrayInput;
        }
}

public class Main {
    static final int size = 100000000;
    // получилось что 100 потоков делают задачу за минимальное время
    static final int countThread = 10;
    static final int h = size / countThread;

    public static void main(String[] args) throws InterruptedException {

        float [] arrayInput = arrayInitialization(size);

        System.out.println("=========================ПЕРВЫЙ МЕТОД СТАРТУЕМ=============================");
        long startTime = System.currentTimeMillis();
        float [] array_1 = arrayProc(arrayInput);
        System.out.println("Время выполнения " + (System.currentTimeMillis() - startTime));

//        for (int i = 0; i < array_1.length; i++) {
//            System.out.println(array_1[i]);
//        }
        System.out.println("=========================ПЕРВЫЙ МЕТОД ОКОНЧЕН=============================");
        array_1 = null;

        System.out.println("=========================ВТОРОЙ МЕТОД СТАРТУЕМ=============================");
        int lengthTempArray;
        MyThread[] thread = new MyThread[countThread];
        float[] array_2 = new float[size];

        startTime = System.currentTimeMillis();

        // делим массив на подмассивы b запучкаем каждый подмасив в отдельном потоке
        for (int i = 0; i < countThread ; i++) {
            lengthTempArray = (i == countThread - 1)?h + (arrayInput.length % h) :h;
            float [] arrayTemp = new float[lengthTempArray];
            System.arraycopy(arrayInput, h * i, arrayTemp, 0, lengthTempArray);
            thread[i] = new MyThread(arrayTemp, h * i);
            thread[i].setName("thread_" + i);
            thread[i].start();
        }

        // ожидаем завершения потоков
        for (int i = 0; i < countThread ; i++) {
            thread[i].join();
            lengthTempArray = (i == countThread - 1)?h + (arrayInput.length % h) :h;
            System.arraycopy(thread[i].getResult(), 0, array_2, h * i, lengthTempArray);
            //System.out.print(String.format("поток %s закончил свою работу\n ", thread[i].getName()));
            // больше этот поток не нужен убиваем его
            //thread[i] = null;
        }

        System.out.println("Время выполнения " + (System.currentTimeMillis() - startTime));
        System.out.println("=========================ВТОРОЙ МЕТОД ОКОНЧЕН=============================");

//        for (int i = 0; i < array_2.length; i++) {
//            System.out.println(array_2[i]);
//        }

    }

    public static float [] arrayProc(float [] arrayInput){

        float [] result = new float[arrayInput.length];

        for (int i = 0; i < arrayInput.length ; i++) {
             result[i] = (float)(arrayInput[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        return result;
    }

    public static float [] arrayInitialization(int arraySize){

        float [] result = new float[arraySize];
        Arrays.fill(result, 1);

        return result;

    }
}

//1. Необходимо написать два метода, которые делают следующее:
//        1) Создают одномерный длинный массив, например:
//static final int size = 10000000;
//static final int h = size / 2;
//        float[] arr = new float[size];
//        2) Заполняют этот массив единицами;
//        3) Засекают время выполнения: long a = System.currentTimeMillis();
//        4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
//        arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//        5) Проверяется время окончания метода System.currentTimeMillis();
//        6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
//        Отличие первого метода от второго:
//        Первый просто бежит по массиву и вычисляет значения.
//        Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
//
//        Пример деления одного массива на два:
//        System.arraycopy(arr, 0, a1, 0, h);
//        System.arraycopy(arr, h, a2, 0, h);
//
//        Пример обратной склейки:
//        System.arraycopy(a1, 0, arr, 0, h);
//        System.arraycopy(a2, 0, arr, h, h);
//
//        Примечание:
//        System.arraycopy() копирует данные из одного массива в другой:
//        System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение,
//        откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
//        По замерам времени:
//        Для первого метода надо считать время только на цикл расчета:
//        for (int i = 0; i < size; i++) {
//        arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//        }
//        Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
