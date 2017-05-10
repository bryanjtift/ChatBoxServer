package me.HeyAwesomePeople.ChatBoxServer.serversettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRank {
    private UUID rankID;
    private String rankName = "Default Rank";

    private Boolean canSeeControlPanel = false;
    private Boolean canKick = false;
    private Boolean canMute = false;
    private Boolean canBanUsername = false;
    private Boolean canBanIpAddress = false;
    private Boolean canLockServer = false;
    private Boolean canShutdownServer = false;

    private Color chatColor = Color.BLACK;
    private Color nameColor = Color.GRAY;

    private List<UUID> clients = new ArrayList<>();

    public ClientRank(UUID uuid) {
        this.rankID = uuid;
    }

    public UUID getUUID() {
        return this.rankID;
    }

    public List<UUID> getClients() {
        return this.clients;
    }

    public void removeClient(UUID client) {
        this.clients.remove(client);
    }

    public void addClient(UUID client) {
        this.clients.add(client);
    }

    public String getRankName() {
        return this.rankName;
    }

    public void setRankName(String name) {
        this.rankName = name;
    }

    public Color getChatColor() {
        return this.chatColor;
    }

    public Color getNameColor() {
        return this.nameColor;
    }

    public void setChatColor(Color color) {
        this.chatColor = color;
    }

    public void setNameColor(Color color) {
        this.nameColor = color;
    }

    public Boolean canKick() {
        return canKick;
    }

    public void setCanKick(boolean canKick) {
        this.canKick = canKick;
    }

    public Boolean canMute() {
        return canMute;
    }

    public void setCanMute(boolean canMute) {
        this.canMute = canMute;
    }

    public Boolean canBanUsername() {
        return canBanUsername;
    }

    public void setCanBanUsername(boolean canBanUsername) {
        this.canBanUsername = canBanUsername;
    }

    public Boolean canBanIpAddress() {
        return canBanIpAddress;
    }

    public void setCanBanIpAddress(boolean canBanIpAddress) {
        this.canBanIpAddress = canBanIpAddress;
    }

    public Boolean canLockServer() {
        return canLockServer;
    }

    public void setCanLockServer(boolean canLockServer) {
        this.canLockServer = canLockServer;
    }

    public Boolean canShutdownServer() {
        return canShutdownServer;
    }

    public void setCanShutdownServer(boolean canShutdownServer) {
        this.canShutdownServer = canShutdownServer;
    }

    public Boolean canSeeControlPanel() {
        return canSeeControlPanel;
    }

    public void setCanSeeControlPanel(boolean canSeeControlPanel) {
        this.canSeeControlPanel = canSeeControlPanel;
    }
}
