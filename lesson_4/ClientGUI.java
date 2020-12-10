package ru.geekbrains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ClientGUI extends JFrame implements KeyListener, ActionListener,
        Thread.UncaughtExceptionHandler {

    private static String NAME_FILE_LOG = "worklog_";
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    private ClientGUI() {

        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUsers = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5", "user6",
                "user_with_an_exceptionally_long_nickname"};
        userList.setListData(users);
        scrollUsers.setPreferredSize(new Dimension(100, 0));

        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addKeyListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUsers, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);

    }

    public void sendMessage(String strMessage){

        log.append(strMessage + "\n");
        writeLog(strMessage);
        tfMessage.setText("");

    }


    public void writeLog(String strLog){


        String nameFile = NAME_FILE_LOG + LocalDate.now().toString() + ".log";
        StringBuilder strTemp = new StringBuilder();
        try {
            strTemp = readTextFile(nameFile);
        }
        catch (IOException IOe){
            // считаем что ситуация нормальная значит за день еще не создан файл (или был поврежден или удален), но ничего мы его далее создадим
        }

        try {
            saveTextFile(nameFile, strTemp + strLog+ "\n");
        }
        catch (IOException IOe){
            // врятли пользователю нужно такое сообщение, хотя тогда он точно сообщит о неполадках (... надо на подумать)
            JOptionPane.showMessageDialog(this, "Error write log file ", "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }



    public static void saveTextFile(String NameFile, String text) throws IOException{

        FileOutputStream fos = new FileOutputStream(NameFile);
        PrintStream ps = new PrintStream(fos);
        ps.print(text);
        ps.close();

    }

    public static StringBuilder readTextFile(String NameFile)throws IOException {
        StringBuilder result = new StringBuilder();

        FileInputStream fis = new FileInputStream(NameFile);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        int b;
        while ((b = isr.read()) != -1){
            result.append((char) b);
        }
        fis.close();

        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        }
        else if(src == btnSend) {
                sendMessage(tfMessage.getText());
            }
        else{
                throw new RuntimeException("Undefined source: " + src);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        Object src = e.getSource();
        if (src == tfMessage && e.getKeyCode()==KeyEvent.VK_ENTER) {
            sendMessage(tfMessage.getText());
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}


