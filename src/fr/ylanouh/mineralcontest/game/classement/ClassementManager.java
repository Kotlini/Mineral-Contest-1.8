package fr.ylanouh.mineralcontest.game.classement;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.GamePlayer;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassementManager {
    private List<Integer> points;

    public ClassementManager(boolean start) {
        if (start) {
            points = new ArrayList<>();
            Main.INSTANCE().getTeamsManager().addPourcentToAllTeam();
            getPlayerKills();
            Main.INSTANCE().getTeamsManager().getTeamList().forEach(teams -> {
                points.add(Main.INSTANCE().getTeamsManager().getTeamPoints(teams));
            });

            Collections.sort(points, Collections.reverseOrder());
            sendTitleByClassement(points);

            for (Player player : Bukkit.getOnlinePlayers()) {
                String message = "§f§m--------§6§l> Mineral Contest <§f§m----------\n" +
                        "§b1er " + getPremier(points).getName() + " | " + Main.INSTANCE().getTeamsManager().getTeamPoints(getPremier(points)) + "\n" +
                        "§e2ième " + getDeuxieme(points).getName() + " | " + Main.INSTANCE().getTeamsManager().getTeamPoints(getDeuxieme(points)) +"\n" +
                        "§c3ième " + getTroisieme(points).getName() + " | " + Main.INSTANCE().getTeamsManager().getTeamPoints(getTroisieme(points)) +"\n" +
                        "§44ième " + getQuatrieme(points).getName() + " | " + Main.INSTANCE().getTeamsManager().getTeamPoints(getQuatrieme(points)) +"\n" +
                        "§f§m-------------------------------------";
                player.sendMessage(message);
            }
        }
    }

    public Teams getPremier(List<Integer> points) {
        for (Teams team : Main.INSTANCE().getTeamsManager().getTeamList()) {
            if (Main.INSTANCE().getTeamsManager().getTeamPoints(team) == points.get(0)) {
                return team;
            }
        }
        return null;
    }

    public Teams getDeuxieme(List<Integer> points) {
        for (Teams team : Main.INSTANCE().getTeamsManager().getTeamList()) {
            if (Main.INSTANCE().getTeamsManager().getTeamPoints(team) == points.get(1)) {
                return team;
            }
        }
        return null;
    }

    public Teams getTroisieme(List<Integer> points) {
        for (Teams team : Main.INSTANCE().getTeamsManager().getTeamList()) {
            if (Main.INSTANCE().getTeamsManager().getTeamPoints(team) == points.get(2)) {
                return team;
            }
        }
        return null;
    }

    public Teams getQuatrieme(List<Integer> points) {
        for (Teams team : Main.INSTANCE().getTeamsManager().getTeamList()) {
            if (Main.INSTANCE().getTeamsManager().getTeamPoints(team) == points.get(3)) {
                return team;
            }
        }
        return null;
    }

    public ClassementEnum getClassementEnumByPlayer(Player player) {
        return Main.INSTANCE().getTeamsManager().getPlayerTeam(player).getClassementEnum();
    }
    public void sendTitleByClassement(List<Integer> points) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (getClassementEnumByPlayer(player) == ClassementEnum.PREMIER) {
                Main.INSTANCE().getTeamsManager().getTeamPlayerList(getPremier(points)).forEach(playerWin -> {
                    playerWin.sendTitle("§2§lWinner", "§fBy Java++");
                });
            } else {
                Main.INSTANCE().getTeamsManager().getPlayerListByRemovePlayerInTeam(
                        getPremier(points)).forEach(playerNoWin -> {
                    playerNoWin.sendTitle("§c§lLooser", "§fBy Java++");
                });
            }
        });
    }

    public String getStringClassementByPlayer(Player player) {
        if (getClassementEnumByPlayer(player) == ClassementEnum.PREMIER) {
            return "1er";
        } else if (getClassementEnumByPlayer(player) == ClassementEnum.DEUXIEME) {
            return "2ième";
        } else if (getClassementEnumByPlayer(player) == ClassementEnum.TROISIEME) {
            return "3ième";
        } else if (getClassementEnumByPlayer(player) == ClassementEnum.QUATRIEME) {
            return "4ième";
        }
        return "§cJavaNullPointerExeption";
    }

    public List<Teams> verifPoints() {
        List<Teams> teams = new ArrayList<>();

        for (Teams team : Main.INSTANCE().getTeamsManager().getTeamList()) {
            //Blue
            if (Main.INSTANCE().getTeamsManager().getTeamPoints(team) ==
                    Main.INSTANCE().getTeamsManager().getTeamPoints(Teams.BLUE) && team != Teams.BLUE) {
                teams.add(team);
                teams.add(Teams.BLUE);
            }

            //Green
            if (Main.INSTANCE().getTeamsManager().getTeamPoints(team) ==
                    Main.INSTANCE().getTeamsManager().getTeamPoints(Teams.DARK_GREEN) && team != Teams.DARK_GREEN) {
                teams.add(team);
                teams.add(Teams.DARK_GREEN);
            }

            //Red
            if (Main.INSTANCE().getTeamsManager().getTeamPoints(team) ==
                    Main.INSTANCE().getTeamsManager().getTeamPoints(Teams.RED) && team != Teams.RED) {
                teams.add(team);
                teams.add(Teams.RED);
            }

            //Yellow
            if (Main.INSTANCE().getTeamsManager().getTeamPoints(team) ==
                    Main.INSTANCE().getTeamsManager().getTeamPoints(Teams.YELLOW) && team != Teams.YELLOW) {
                teams.add(team);
                teams.add(Teams.YELLOW);
            }
        }
        return teams;
    }

    public void getPlayerKills() {
        List<Integer> kills = new ArrayList<>();

        if (verifPoints().size() == 0) {
            return;
        }

        for (Teams team : verifPoints()) {
            for (Player player : Main.INSTANCE().getTeamsManager().getTeamPlayerList(team)) {
                GamePlayer gp = GamePlayer.getGamePlayer(player.getUniqueId());
                kills.add(gp.getKills());
            }
        }

        Collections.sort(kills, Collections.reverseOrder());

        int kill1 = 0;
        int kill2 = 0;
        int kill3 = 0;

        if (kills.get(0) != null) {
            kill1 = kills.get(0)+2;
        }
        if (kills.get(1) != null) {
            kill2 = kills.get(1)+1;
        }
        if (kills.get(2) != null) {
            kill3 = kills.get(2);
        }

        Teams team1 = GamePlayer.getTeamPlayerByKill(kill1);
        Teams team2 = GamePlayer.getTeamPlayerByKill(kill2);
        Teams team3 = GamePlayer.getTeamPlayerByKill(kill3);

        Main.INSTANCE().getTeamsManager().setTeamPoints(team1,
                Main.INSTANCE().getTeamsManager().getTeamPoints(team1) + 20);
        Main.INSTANCE().getTeamsManager().setTeamPoints(team2,
                Main.INSTANCE().getTeamsManager().getTeamPoints(team2) + 20);
        Main.INSTANCE().getTeamsManager().setTeamPoints(team3,
                Main.INSTANCE().getTeamsManager().getTeamPoints(team3) + 20);
    }
}