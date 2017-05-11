package me.HeyAwesomePeople.ChatBoxServer;

import me.HeyAwesomePeople.ChatBoxServer.clients.SubServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener extends Thread {

    private Main main;
    private ServerSocket listener;

    final private SubServer[] clientConnections;

    ConnectionListener(Main main, ServerSocket listener) {
        clientConnections = new SubServer[main.settings.MAX_CONNECTIONS];

        this.main = main;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            //noinspection InfiniteLoopStatement
            while (!this.isInterrupted()) {
                Socket socket = listener.accept();
                assignConnectionToSubServer(socket);
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

    /*
    Finds the first open connection in the array. If it cannot find one, the socket won't connect.
     */
    public void assignConnectionToSubServer(Socket connection) {
        for (int i = 0; i < main.settings.MAX_CONNECTIONS; i++) {
            if (this.clientConnections[i] == null) {
                this.clientConnections[i] = new SubServer(main, connection, i);
                break;
            }
        }
    }


}
