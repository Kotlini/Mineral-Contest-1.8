package fr.ylanouh.mineralcontest.game.teams;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.classement.ClassementEnum;
import fr.ylanouh.mineralcontest.game.cuboid.Cuboid;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamsManager {

    private int teamCount;

    private int teamSize;

    private final List<Teams> teamList = new ArrayList<>();

    private final Map<Teams, List<Player>> playersTeam = new HashMap<>();

    private Map<Teams, Integer> teamsPoints = new HashMap<>();

    private Map<Teams, Integer> teamsBoost = new HashMap<>();

    private final Map<Teams, Location> teamsLocation = new HashMap<>();

    private final Map<Teams, Cuboid> teamsCuboid = new HashMap<>();

    public TeamsManager(int teamCount, int teamSize, boolean teamEnable) {
        this.teamCount = teamCount;
        if (teamCount > 1 || teamEnable) {
            if (this.teamCount > (Teams.values()).length - 2)
                this.teamCount = (Teams.values()).length - 2;
            this.teamSize = teamSize;
            int teamFilled = 0;
            for (Teams teams : Teams.values()) {
                if (teamFilled >= teamCount)
                    break;
                this.teamList.add(teams);
                this.playersTeam.put(teams, new ArrayList<>());
                teamFilled++;
            }
        }
    }



    public int countPlayersInTeam(Teams teams) {
        return ((List)this.playersTeam.get(teams)).size();
    }

    public void addPlayerTeam(Player player, Teams teams) {
        this.playersTeam.computeIfAbsent(teams, k -> new ArrayList());
        ((List<Player>)this.playersTeam.get(teams)).add(player);
        TeamsTags.setNameTag(player, teams.getName(), teams.getPrefix() + " ");
    }

    public void removePlayerTeam(Player player, Teams teams) {
        ((List)this.playersTeam.get(teams)).remove(player);
        TeamsTags.setNameTag(player, ChatColor.WHITE + player.getName(), ChatColor.WHITE + "");
    }

    public void removePlayerFromAll(Player player) {
        for (Teams teams : this.teamList)
            removePlayerTeam(player, teams);
    }

    public boolean isTeamFull(Teams teams) {
        return (((List)this.playersTeam.get(teams)).size() >= this.teamSize);
    }

    public boolean isInTeamPlayer(Player player, Teams teams) {
        return ((List)this.playersTeam.get(teams)).contains(player);
    }

    public boolean hasTeamPlayer(Player player) {
        for (Teams teams : Teams.values()) {
            if (isInTeamPlayer(player, teams))
                return true;
        }
        return false;
    }

    public Teams getPlayerTeam(Player player) {
        for (Teams teams : this.teamList) {
            if (isInTeamPlayer(player, teams))
                return teams;
        }
        return null;
    }

    public Integer countTeam(Teams teams) {
        return Integer.valueOf(((List)this.playersTeam.get(teams)).size());
    }

    public void setSpectator(Player player) {
        removePlayerFromAll(player);
        player.setGameMode(GameMode.SPECTATOR);
        TeamsTags.setNameTag(player, "Spectateur", ChatColor.GRAY + "[SPECT] ");
    }

    public boolean isSpectator(Player player) {
        return (player.getGameMode() == GameMode.SPECTATOR);
    }

    public void addInRandomTeam(Player player) {
        Teams teamToJoin = null;
        for (Teams teams : this.teamList) {
            if (!isTeamFull(teams) && (teamToJoin == null || countTeam(teams).intValue() < countTeam(teamToJoin).intValue()))
                teamToJoin = teams;
        }
        addPlayerTeam(player, teamToJoin);
    }

    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount.intValue();
        if (this.teamCount > (Teams.values()).length - 2)
            this.teamCount = (Teams.values()).length - 2;
        Bukkit.getOnlinePlayers().forEach(this::removePlayerFromAll);
        this.teamList.clear();
        this.playersTeam.clear();
        int teamFilled = 0;
        for (Teams teams : Teams.values()) {
            if (teamFilled >= teamCount.intValue())
                break;
            this.teamList.add(teams);
            this.playersTeam.put(teams, new ArrayList<>());
            teamFilled++;
        }
    }

    public List<Player> getTeamPlayerList(Teams teams) {
        if (this.playersTeam.get(teams) == null)
            return new ArrayList<>();
        return this.playersTeam.get(teams);
    }

    public void setTeamSize(Integer teamSize) {
        this.teamSize = teamSize.intValue();
    }

    public void setTeamLocation(Teams teams, Location location) {
        this.teamsLocation.put(teams, location);
    }

    public void setTeamPoints(Teams teams, int teamPoints) {
        if (this.teamsPoints.get(teams) == null) {
            this.teamsPoints.put(teams, teamPoints);
        }else {
            this.teamsPoints.replace(teams, teamPoints);
        }
    }

    public void setTeamBoost(Teams teams, int teamBoost) {
        if (this.teamsBoost.get(teams) == null) {
            this.teamsBoost.put(teams, teamBoost);
        }else {
            this.teamsBoost.replace(teams, teamBoost);
        }
    }

    public Location getTeamLocation(Teams teams) {
        if (this.teamsLocation.get(teams) != null)
            return this.teamsLocation.get(teams);
        return new Location(Bukkit.getWorld("world"), 0.0D, 0.0D, 0.0D);
    }

    public Integer getTeamPoints(Teams teams) {
        if (this.teamsPoints.get(teams) != null)
            return this.teamsPoints.get(teams);
        return null;
    }

    public Integer getTeamBoost(Teams teams) {
        if (this.teamsBoost.get(teams) != null)
            return this.teamsBoost.get(teams);
        return null;
    }

    public Integer getTeamCount() {
        return Integer.valueOf(this.teamCount);
    }

    public Integer getTeamSize() {
        return Integer.valueOf(this.teamSize);
    }

    public List<Teams> getTeamList() {
        return this.teamList;
    }

    public void removePointsAllTeams(int points, Teams team) {
        for (Teams teams : getTeamList()) {
            if (teams != team) {
                setTeamPoints(teams, getTeamPoints(teams)-points);
            }
        }
    }

    public Map<Teams, Cuboid> getTeamsCuboid() {
        return teamsCuboid;
    }
//s
    public static Location getLocArenaToTeam(Teams team) {
        World world = Bukkit.getWorld(Main.INSTANCE().getConfig().getString("worldname"));
        if (team == Teams.BLUE) {
            return Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("arena.posBlue"), world);
        } else if (team == Teams.RED) {
            return Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("arena.posRed"), world);
        } else if (team == Teams.YELLOW) {
            return Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("arena.posYellow"), world);
        } else {
            return Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("arena.posGreen"), world);
        }
    }

    public Teams getPointsTeam(Integer p) {
        for (Teams teams : getTeamList()) {
            if (getTeamPoints(teams) == p) {
                return teams;
            }
        }
        return null;
    }

    public Map<Teams, Integer> getTeamsPoints() {
        return teamsPoints;
    }

    public Map<Teams, Integer> getTeamsBoost() {
        return teamsBoost;
    }

    public void setClassement(List<Integer> pointsList) {
        int point1 = pointsList.get(0);
        int point2 = pointsList.get(1);
        int point3 = pointsList.get(2);
        int point4 = pointsList.get(3);
        for (Teams team : getTeamList()) {
            System.out.println(Main.INSTANCE().getClassementManager());
            if (getTeamPoints(team) == point1) {
                team.setClassementEnum(ClassementEnum.PREMIER);
            } else if (getTeamPoints(team) == point2) {
                team.setClassementEnum(ClassementEnum.DEUXIEME);
            } else if (getTeamPoints(team) == point3) {
                team.setClassementEnum(ClassementEnum.TROISIEME);
            } else if (getTeamPoints(team) == point4) {
                team.setClassementEnum(ClassementEnum.QUATRIEME);
            }
        }
    }

    public List<Player> getPlayerListByRemovePlayerInTeam(Teams team) {
        List<Player> playerList = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (getPlayerTeam(player) != team) {
                playerList.add(player);
            }
        });
        return playerList;
    }

    public void addPourcentToAllTeam() {
        for (Teams team : getTeamList()) {
            int boost = getTeamBoost(team);
            int pointsTotal = getTeamPoints(team) * boost / 100;
            if (boost != 0) {
                setTeamPoints(team, getTeamPoints(team)+pointsTotal);
            }
        }
    }

}