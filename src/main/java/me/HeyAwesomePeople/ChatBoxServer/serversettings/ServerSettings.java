package me.HeyAwesomePeople.ChatBoxServer.serversettings;

import java.util.ArrayList;
import java.util.List;

public class ServerSettings {

    private List<ClientRank> ranks = new ArrayList<>();

    public ServerSettings() {
        ranks = loadRanks();
    }


    private List<ClientRank> loadRanks() {
        List<ClientRank> ranks = new ArrayList<>();
        // TODO load ranks from MySQL
        return ranks;
    }

}

