package me.HeyAwesomePeople.ChatBoxServer.userdata;

import java.io.*;
import java.util.HashMap;

public class UserData {

    private static final File f = new File(System.getProperty("user.home") + "\\chatbox\\users.txt");
    private static final File fDir = new File(System.getProperty("user.home") + "\\chatbox");

    // username:password
    public HashMap<String, String> users = new HashMap<String, String>();

    public UserData() {
        if (!f.exists()) {
            fDir.mkdir();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadUserData();
    }

    private void loadUserData() {
        String line = null;

        try {
            FileReader reader = new FileReader(f);
            BufferedReader buffReader = new BufferedReader(reader);

            while ((line = buffReader.readLine()) != null) {
                String[] data = line.split(":");
                users.put(data[0], data[1]);
            }

            buffReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserData() {
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(f, false));
            for (String username : users.keySet()) {
                writer.println(username + ":" + users.get(username));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerNewUser(String user, String pass) {
        this.users.put(user, pass);
        this.saveUserData();
    }

    public boolean isUserValid(String username) {
        return this.users.containsKey(username);
    }

    public boolean isPasswordValid(String username, String password) {
        return this.users.containsKey(username) && this.users.get(username).equals(password);
    }

    public boolean isUsernameTaken(String username) {
        return this.users.containsKey(username);
    }

}
