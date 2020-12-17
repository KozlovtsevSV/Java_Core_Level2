package ru.geekbrains.java_two.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

public class ServerSocketThread extends Thread {
    private int port;
    private int timeout;
    ServerSocketThreadListener listener;
    private Vector<Socket> onLineUsers = new Vector();
    public String allMsg = null;

    public ServerSocketThread(ServerSocketThreadListener listener, String name, int port, int timeout) {
        super(name);
        this.port = port;
        this.timeout = timeout;
        this.listener = listener;
        start();
    }

    public Vector<Socket> getOnLineUsers() {
        return onLineUsers;
    }

    @Override
    public void run() {
        listener.onServerStart(this);
        try (ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(timeout);
            listener.onServerSocketCreated(this, server);
            while (!isInterrupted()) {
                Socket socket = null;
                try {
                    socket = server.accept();

                    onLineUsers.add(socket);
                   // onLineUsers.get()
                    listener.onSocketAccepted(this, server, socket);
                } catch (SocketTimeoutException e) {
                    listener.onServerTimeout(this, server);
                }

                if(allMsg != null){
                    DataOutputStream out = null;
                    for (int i = 0; i < onLineUsers.size(); i++) {
                        out = new DataOutputStream(onLineUsers.get(i).getOutputStream());
                        out.writeUTF(allMsg);
                    }
                    allMsg = null;
                }
            }
        } catch (IOException e) {
            listener.onServerException(this, e);
        } finally {
            listener.onServerStop(this);
        }



    }
}
