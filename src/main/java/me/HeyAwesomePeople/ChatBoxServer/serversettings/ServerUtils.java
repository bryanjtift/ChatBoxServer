package me.HeyAwesomePeople.ChatBoxServer.serversettings;

import java.util.HashMap;
import java.util.UUID;

public class ServerUtils {

    public HashMap<UUID, HashMap<String, String>> rankToHashmap(ClientRank rank) {
        HashMap<UUID, HashMap<String, String>> top = new HashMap<>();

        HashMap<String, String> data = new HashMap<>();
        data.put("rankName", rank.getRankName());
        data.put("chatColor", String.format("#%06x", rank.getChatColor().getRGB() & 0x00FFFFFF));
        data.put("nameColor", String.format("#%06x", rank.getNameColor().getRGB() & 0x00FFFFFF));
        data.put("canSeeControlPanel", rank.canSeeControlPanel().toString());
        data.put("canKick", rank.canKick().toString());
        data.put("canMute", rank.canMute().toString());
        data.put("canBanUsername", rank.canBanUsername().toString());
        data.put("canBanIpAddress", rank.canBanIpAddress().toString());
        data.put("canLockServere", rank.canLockServer().toString());
        data.put("canShutdownServer", rank.canShutdownServer().toString());

        top.put(rank.getUUID(), data);
        return top;
    }

}
