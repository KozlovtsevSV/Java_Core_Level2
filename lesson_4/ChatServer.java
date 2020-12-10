package ru.geekbrains;

public class ChatServer {

    private int port;

    public void start(int port) {
        System.out.println("Server started at port " + port);
    }

    public void stop() {
        System.out.println("Server stopped");
    }

}
//    Отправлять сообщения в лог по нажатию кнопки или по нажатию клавиши Enter.
//        Создать лог в файле (показать комментарием, где и как Вы планируете писать сообщение в файловый журнал).
//        Прочитать методичку к следующему уроку