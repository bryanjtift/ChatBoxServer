package me.HeyAwesomePeople.ChatBoxServer;

import me.HeyAwesomePeople.ChatBoxServer.userdata.UserData;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;

public class Main {

    public UserData userData;
    private ServerSettings settings;

    private ConnectionListener connectionListener;

    private ServerSocket listener;
    static final HashSet<String> names = new HashSet<String>();
    static final HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    private Main() {
        userData = new UserData();
        settings = new ServerSettings();

        try {
            listener = new ServerSocket(settings.PORT);
            Log.info("The chat server is running. Settings={port=" + settings.PORT + ", maxclients=" + settings.MAX_CONNECTIONS + ", moderatoroverride="
                    + settings.MOD_OVERRIDE_MAX_CONNECTIONS + "}");
            startSocketAcceptor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startSocketAcceptor() {
        connectionListener = new ConnectionListener(this, listener);
        connectionListener.run();
    }

    public static void main(String[] args) {
        new Main();
    }

}
