package me.HeyAwesomePeople.ChatBoxServer;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionListener implements Runnable {

    private Main main;
    private ServerSocket listener;

    ConnectionListener(Main main, ServerSocket listener) {
        this.main = main;
        this.listener = listener;
    }

    public void run() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
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
}
