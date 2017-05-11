package me.HeyAwesomePeople.ChatBoxServer.serversettings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServerSettings {

    public int PORT = 9001;
    public int MAX_CONNECTIONS = 2;
    public boolean MOD_OVERRIDE_MAX_CONNECTIONS = true;

    private static final File f = new File(System.getProperty("user.home") + "\\chatbox\\server.properties");
    private static final File fDir = new File(System.getProperty("user.home") + "\\chatbox");

    private List<ClientRank> ranks = new ArrayList<>();

    public ServerSettings() {
        ranks = loadRanks();

        if (f.exists()) {
            loadParams();
        } else {
            saveParams();
        }
    }

    public void setMaxClients(Integer maxClients) {
        this.MAX_CONNECTIONS = maxClients;
    }

    public Integer getMaxClients() {
        return this.MAX_CONNECTIONS;
    }

    private List<ClientRank> loadRanks() {
        List<ClientRank> ranks = new ArrayList<>();
        // TODO load ranks from MySQL
        return ranks;
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

