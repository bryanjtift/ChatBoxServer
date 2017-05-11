package me.HeyAwesomePeople.ChatBoxServer.clients;

import me.HeyAwesomePeople.ChatBoxServer.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SubServer extends Thread {

    final private Main main;
    final private Socket socket;
    final private int id;

    private boolean loggedIn = false;

    private String username;
    private BufferedReader in;
    private PrintWriter out;

    final private String LOGIN = "0x01";
    final private String REGISTER = "0x02";
    final private String COMMAND = "0x03";

    public SubServer (Main main, Socket socket, int id) {
        this.main = main;
        this.socket = socket;
        this.id = id;

        start();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("connect.");
            String line;
            while (true) {
                line = in.readLine();
                String protocol = line.substring(0, 4);
                System.out.println("data: " + line);
                System.out.println("data, substring 5: " + line.substring(5));
                System.out.println("data, cmd: " + protocol);
                switch (protocol) {
                    case LOGIN:
                        processLogin(line.substring(5));
                        break;
                    case REGISTER:
                        processRegister(line.substring(5));
                        break;
                    case COMMAND:
                        processCommand(line.substring(5));
                        break;
                }
            }

            /*
            while (!this.isInterrupted()) {
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
            */

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

    public void processLogin(String line) {
        if (!line.contains(":")) {
            out.println("INVALIDLOGIN");
            return;
        }
        String[] data = line.split(":");
        String username = data[0];
        String password = data[1];
        if (main.userData.isUserValid(username)) {
            if (main.userData.isPasswordValid(username, password)) {
                out.println("LOGINACCEPTED");

                setUsername(username);
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
    }

    public void processRegister(String line) {
        String[] data = line.split(":");
        String username = data[0];
        String password = data[1];
        if (main.userData.isUsernameTaken(username)) {
            out.println("USERNAMETAKEN");
        } else {
            synchronized (this) {
                main.userData.registerNewUser(username, password);
            }
            out.println("REGISTERSUCCESS");
        }
    }

    public void processCommand(String line) {
        if (line.startsWith("OUT")) {
            if (loggedIn) {
                for (PrintWriter writer : Main.writers) {
                    writer.println("MESSAGE " + username + ": " + line.substring(4));
                }
            }
        } else if (line.startsWith("LOGOUT")) {
            main.writers.remove(out);
            loggedIn = false;
            setUsername("");
        }
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException ignored) { }
    }

    private void setUsername(String s) {
        this.username = s;
    }

}
