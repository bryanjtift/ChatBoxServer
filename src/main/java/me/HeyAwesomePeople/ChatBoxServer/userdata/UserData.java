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

    public String registerNewUser(String user, String pass) {
        try {
            PrintWriter out = new PrintWriter(f);
            out.println(user + ":" + pass);
            out.close();
            return "REGISTERED";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "ERROR: R0";
    }

}
