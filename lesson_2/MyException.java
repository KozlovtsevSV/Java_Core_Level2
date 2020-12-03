package ru.geekbrains;

import java.io.IOException;
import java.io.PrintStream;

public class MyException extends IOException {

    public MyException(String message) {
       super(message);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        System.out.println("Сработал мой класс Exception");
        StackTraceElement[]  ste = getStackTrace();

        for (int i = 0; i < ste.length; i++) {
            System.out.println("В классе: " + ste[i].getClassName() + " " + ste[i].getFileName() + " произошла ошибка: " +getMessage());
        }

    }
}
