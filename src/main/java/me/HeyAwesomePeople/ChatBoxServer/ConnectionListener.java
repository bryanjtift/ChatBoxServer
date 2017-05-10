package me.HeyAwesomePeople.ChatBoxServer;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionListener implements Runnable {

    private Main main;
    private boolean listen = true;
    private ServerSocket listener;

    ConnectionListener(Main main, ServerSocket listener) {
        this.main = main;
        this.listener = listener;
    }

    public void run() {
        try {
            while (listen) {
                new Handler(main, listener.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() throws IOException {
        listen = false;
        listener.close();
    }
}
