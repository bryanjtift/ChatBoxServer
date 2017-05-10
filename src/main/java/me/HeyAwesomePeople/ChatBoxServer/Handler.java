package me.HeyAwesomePeople.ChatBoxServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Handler extends Thread {

    private Main main;
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
                    if (main.userData.users.containsKey(data[0])) {
                        out.println("USERNAMETAKEN");
                    } else {
                        synchronized (this) {
                            main.userData.users.put(data[0], data[1]);
                            main.userData.saveUserData();
                        }
                        out.println("REGISTERSUCCESS");
                    }
                } else if (login.startsWith("LOGIN")) {
                    if (!login.contains(":")) {
                        out.println("INVALIDLOGIN");
                        continue;
                    }
                    String[] data = login.substring(6).split(":");
                    if (main.userData.users.containsKey(data[0])) {
                        if (main.userData.users.get(data[0]).equals(data[1])) {
                            out.println("LOGINACCEPTED");
                            username = data[0];
                            break;
                        } else {
                            out.println("PASSWORDINVALID");
                        }
                    } else {
                        out.println("USERNAMEINVALID");
                    }
                }
            }

            main.writers.add(out);

            while (true) {
                String input = in.readLine();
                if (input == null || input.equals("")) return;

                if (input.startsWith("LOGOUT")) {
                    data = input.substring(7);
                }

                for (PrintWriter writer : Main.writers) {
                    writer.println("MESSAGE " + username + ": " + input);
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

}
