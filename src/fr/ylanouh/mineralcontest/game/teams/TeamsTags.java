package fr.ylanouh.mineralcontest.game.teams;

import java.util.Collection;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamsTags {
    private String prefix;

    private Team team;

    public static Scoreboard scoreboard;

    public TeamsTags(String name, String prefix, Scoreboard current) throws Exception {
        this.prefix = prefix;
        this.team = current.getTeam(name);
        if (this.team == null)
            this.team = current.registerNewTeam(name);
        scoreboard = current;
        this.team.setCanSeeFriendlyInvisibles(false);
        this.team.setAllowFriendlyFire(false);
        int prefixLength = 0;
        int suffixLength = 0;
        if (prefix != null)
            prefixLength = prefix.length();
        if (prefixLength + suffixLength >= 32)
            throw new Exception("prefix and suffix lenghts are greater than 16");
        if (prefix != null)
            this.team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
    }

    public TeamsTags(String name, String prefix) throws Exception {
        this(name, prefix, Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public void set(Player player) {
        this.team.addPlayer((OfflinePlayer)player);
        player.setScoreboard(scoreboard);
    }

    public void remove(Player player) {
        this.team.removePlayer((OfflinePlayer)player);
    }

    public void resetTagUtils(UUID uuid) {
        remove(Bukkit.getPlayer(uuid));
    }

    public void setAll(Collection<Player> players) {
        for (Player player : players)
            set(player);
    }

    public void setAll(Player[] players) {
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = players).length;
        for (int i = 0; i < j; i++) {
            Player player = arrayOfPlayer[i];
            set(player);
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        this.team.setPrefix(this.prefix);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Team getTeam() {
        return this.team;
    }

    public void removeTeam() {
        this.team.unregister();
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static void setNameTag(Player player, String name, String prefix) {
        try {
            TeamsTags tagplayer = new TeamsTags(name, prefix);
            tagplayer.set(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
