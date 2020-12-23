package ru.geekbrains.java_two.chat.server.gui;

import ru.geekbrains.java_two.chat.server.core.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener,
        Thread.UncaughtExceptionHandler {

    private static final int POS_X = 1000;
    private static final int POS_Y = 550;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private final ChatServer chatServer = new ChatServer();
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // Event Dispatching Thread
            @Override
            public void run() {
                new ServerGUI(); // Event Queue
            }
        });
    }

    private ServerGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLayout(new GridLayout(2, 2));
        btnStop.addActionListener(this);
        btnStart.addActionListener(this);
        btnSend.addActionListener(this);

        add(btnStart);
        add(btnStop);
        add(tfMessage);
        add(btnSend);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnStart) {
            chatServer.start(8189);
        } else if (src == btnStop) {
            //throw new RuntimeException("Hello from EDT!");
            chatServer.stop();
        } else if (src == btnSend) {
            chatServer.sendMessageAll(tfMessage.getText());
            tfMessage.setText("");
        } else {
            throw new RuntimeException("Undefined source: " + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StackTraceElement[] ste = e.getStackTrace();
        String msg = String.format("Exception in thread \"%s\" %s: %s\n\tat %s",
                t.getName(), e.getClass().getCanonicalName(),
                e.getMessage(), ste[0]);
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
