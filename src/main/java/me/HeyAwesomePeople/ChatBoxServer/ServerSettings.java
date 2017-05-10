package me.HeyAwesomePeople.ChatBoxServer;

import java.io.*;
import java.util.Properties;

class ServerSettings {

    int PORT = 9001;
    int MAX_CONNECTIONS = 2;
    boolean MOD_OVERRIDE_MAX_CONNECTIONS = true;

    private static final File f = new File(System.getProperty("user.home") + "\\chatbox\\server.properties");
    private static final File fDir = new File(System.getProperty("user.home") + "\\chatbox");

    ServerSettings() {
        if (f.exists()) {
            loadParams();
        } else {
            saveParams();
        }
    }

    private void loadParams() {
        Properties props = new Properties();
        InputStream is;

        try {
            if (!f.exists()) return;
            is = new FileInputStream(f);
        } catch (Exception e) {
            is = null;
        }

        try {
            if (is == null) {
                is = getClass().getResourceAsStream(f.toString());
            }

            props.load(is);
        } catch (Exception ignored) {
        }

        PORT = Integer.parseInt(props.getProperty("ServerPort", "9001"));
        MAX_CONNECTIONS = Integer.parseInt(props.getProperty("MaxClients", "2"));
        MOD_OVERRIDE_MAX_CONNECTIONS = Boolean.parseBoolean(props.getProperty("ModeratorMaxConnectionOverride", "true"));
    }

    private void saveParams() {
        boolean fileCont = true;

        try {
            if (!f.exists()) {
                fDir.mkdir();
                fileCont = f.createNewFile();
            }

            if (!fileCont) {
                System.out.println("Failed to save properties file.");
                System.exit(0);
            }

            Properties props = new Properties();
            props.setProperty("ServerPort", "" + PORT);
            props.setProperty("MaxClients", "" + MAX_CONNECTIONS);
            props.setProperty("ModeratorMaxConnectionOverride", "true");

            OutputStream out = new FileOutputStream(f);
            props.store(out, "This is an optional header comment string");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
