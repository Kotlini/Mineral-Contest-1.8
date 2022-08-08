package fr.ylanouh.mineralcontest.game;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.scoreboard.FastBoard;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GamePlayer {
    private static final HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();

    private final UUID uuid;

    private int kills = 0;

    private int deaths = 0;

    private Teams teams = null;

    private boolean connected = true;

    private FastBoard fastboard;

    public GamePlayer(UUID uuid, FastBoard fastboard) {
        this.uuid = uuid;
        this.fastboard = fastboard;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public Teams getTeams() {
        return this.teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    public void remove(UUID uuid) {
        gamePlayers.remove(uuid);
    }

    public static GamePlayer getGamePlayer(UUID uuid) {
        return gamePlayers.get(uuid);
    }

    public void addPlayer() {
        gamePlayers.put(this.getUuid(), this);
    }

    public static HashMap<UUID, GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public FastBoard getFastboard() {
        return fastboard;
    }

    public void setFastboard(FastBoard fastboard) {
        this.fastboard = fastboard;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static Teams getTeamPlayerByKill(int kills) {
        for (GamePlayer gp : getGamePlayers().values()) {
            if (gp.getKills() == kills) {
                if (Bukkit.getPlayer(gp.getUuid()) != null) {
                    return Main.INSTANCE().getTeamsManager().getPlayerTeam(Bukkit.getPlayer(gp.getUuid()));
                }
            }
        }
        return null;
    }
}