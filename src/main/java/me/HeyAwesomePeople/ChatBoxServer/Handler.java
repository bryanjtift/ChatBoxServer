package me.HeyAwesomePeople.ChatBoxServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Handler extends Thread {

    private Main main;

    private boolean loggedIn = false;

    private String username;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String data;

    public Handler(Main main, Socket socket) {
        this.main = main;
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String login = in.readLine();
                System.out.println("data: " + login);
                if (login == null || login.equals("")) continue;

                if (login.startsWith("REGISTER")) {
                    String[] data = login.substring(9).split(":");
                    if (main.userData.isUsernameTaken(data[0])) {
                        out.println("USERNAMETAKEN");
                    } else {
                        synchronized (this) {
                            main.userData.registerNewUser(data[0], data[1]);
                        }
                        out.println("REGISTERSUCCESS");
                    }
                } else if (login.startsWith("LOGIN")) {
                    if (!login.contains(":")) {
                        out.println("INVALIDLOGIN");
                        continue;
                    }
                    String[] data = login.substring(6).split(":");
                    if (main.userData.isUserValid(data[0])) {
                        if (main.userData.isPasswordValid(data[0], data[1])) {
                            out.println("LOGINACCEPTED");

                            setUsername(data[0]);
                            main.writers.add(out);
                            for (PrintWriter writer : Main.writers) {
                                if (writer == out) continue;
                                writer.println("USERJOIN " + username);
                            }
                            loggedIn = true;

                        } else {
                            out.println("PASSWORDINVALID");
                        }
                    } else {
                        out.println("USERNAMEINVALID");
                    }
                } else if (login.startsWith("OUT")) {
                    if (loggedIn) {
                        for (PrintWriter writer : Main.writers) {
                            writer.println("MESSAGE " + username + ": " + login.substring(4));
                        }
                    }
                } else if (login.startsWith("LOGOUT")) {
                    main.writers.remove(out);
                    loggedIn = false;
                    setUsername("");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (username != null) {
                Main.names.remove(username);
            }
            if (out != null) {
                Main.writers.remove(out);
            }
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }

    }

    private void setUsername(String s) {
        this.username = s;
    }

}
