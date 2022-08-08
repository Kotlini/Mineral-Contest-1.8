package fr.ylanouh.mineralcontest.game;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.cuboid.CuboidManager;
import fr.ylanouh.mineralcontest.game.door.Door;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.game.cuboid.Cuboid;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
public class GameSettings {
    private final String gametype;

    private final int minPlayers;

    private final int maxPlayers;

    private final int countdownStartTimer;

    private final Location lobbySpawn;

    private final boolean useTeams;

    private final int teamSize;

    private final int teamsCount;

    private final GameMode gamemode;

    private final GameMode lobbymode;

    private final GameMode spectatorMode;

    private final int foodLevel;

    private final double healthLevel;

    public GameSettings(String gametype, int minPlayers, int maxPlayers, int countdownStartTimer, Location lobbySpawn, boolean useTeams, int teamSize, int teamsCount, GameMode gamemode, GameMode lobbymode, GameMode spectatorMode, int foodLevel, double healthLevel) {
        this.gametype = gametype.replaceAll("&", "§");
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.countdownStartTimer = countdownStartTimer;
        this.lobbySpawn = lobbySpawn;
        this.useTeams = useTeams;
        this.gamemode = gamemode;
        this.lobbymode = lobbymode;
        this.spectatorMode = spectatorMode;
        this.foodLevel = foodLevel;
        this.healthLevel = healthLevel;
        this.teamSize = teamSize;
        this.teamsCount = teamsCount;

        World world = Bukkit.getWorld(Main.INSTANCE().getConfig().getString("worldname"));
        Cuboid red = new Cuboid(
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("teams.red.protection.pos1"),
                        world),
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("teams.red.protection.pos2"),
                        world));

        Cuboid blue = new Cuboid(
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("teams.blue.protection.pos1"),
                        world),
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("teams.blue.protection.pos2"),
                        world));

        Cuboid green = new Cuboid(
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("teams.green.protection.pos1"),
                        world),
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("teams.green.protection.pos2"),
                        world));

        Cuboid yellow = new Cuboid(
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("teams.yellow.protection.pos1"),
                        world),
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("teams.yellow.protection.pos2"),
                        world));

        Cuboid arena = new Cuboid(
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("arenaProtect.pos1"),
                        world),
                Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("arenaProtect.pos2"),
                        world));

        CuboidManager redC = new CuboidManager(Teams.RED, red);
        redC.addCuboid();
        CuboidManager blueC = new CuboidManager(Teams.BLUE, blue);
        blueC.addCuboid();
        CuboidManager greenC = new CuboidManager(Teams.DARK_GREEN, green);
        greenC.addCuboid();
        CuboidManager yellowC = new CuboidManager(Teams.YELLOW, yellow);
        yellowC.addCuboid();
        CuboidManager arenaC = new CuboidManager(Teams.ARENA, arena);
        arenaC.addCuboid();

        new Door(Teams.DARK_GREEN, new Location(world, -181, 62, -183)).add();
        new Door(Teams.BLUE, new Location(world,-181, 62, -271)).add();
        new Door(Teams.YELLOW, new Location(world, -225, 62, -227)).add();
        new Door(Teams.RED, new Location(world, -137, 62, -227)).add();

        world.setWeatherDuration(0);
    }

    public String getGametype() {
        return this.gametype;
    }

    public int getMinPlayers() {
        return this.minPlayers;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getCountdownStartTimer() {
        return this.countdownStartTimer;
    }

    public Location getLobbySpawn() {
        return this.lobbySpawn;
    }

    public boolean isUseTeams() {
        return this.useTeams;
    }

    public GameMode getGamemode() {
        return this.gamemode;
    }

    public GameMode getLobbymode() {
        return this.lobbymode;
    }

    public GameMode getSpectatorMode() {
        return this.spectatorMode;
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public double getHealthLevel() {
        return this.healthLevel;
    }

    public int getTeamSize() {
        return this.teamSize;
    }

    public int getTeamsCount() {
        return this.teamsCount;
    }
}