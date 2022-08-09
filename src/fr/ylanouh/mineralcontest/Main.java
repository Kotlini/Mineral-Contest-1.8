package fr.ylanouh.mineralcontest;

import fr.ylanouh.mineralcontest.commands.*;
import fr.ylanouh.mineralcontest.game.GameSettings;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.Map.WorldManager;
import fr.ylanouh.mineralcontest.game.animations.Chest;
import fr.ylanouh.mineralcontest.game.classement.ClassementManager;
import fr.ylanouh.mineralcontest.game.cuboid.CuboidManager;
import fr.ylanouh.mineralcontest.game.decogame.DecoGameManager;
import fr.ylanouh.mineralcontest.game.door.Door;
import fr.ylanouh.mineralcontest.game.runnables.GameRunnable;
import fr.ylanouh.mineralcontest.game.runnables.LobbyRunnable;
import fr.ylanouh.mineralcontest.game.scoreboard.FastBoard;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.game.teams.TeamsManager;
import fr.ylanouh.mineralcontest.listeners.*;
import fr.ylanouh.mineralcontest.game.cuboid.Cuboid;
import fr.ylanouh.mineralcontest.listeners.EventListener;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends JavaPlugin {

    private GameSettings gameSettings;
    private TeamsManager teamsManager;
    private LobbyRunnable lobbyRunnable;

    private GameRunnable gameRunnable;

    private ClassementManager classementManager;

    public Map<Location, Chest> locationChestMap;

    public Map<Teams, CuboidManager> teamCuboidMap;
    public Map<UUID, DecoGameManager> uuidDecoGameManagerMap;

    public Map<Teams, Door> teamDoorMap;

    private static Main INSTANCE = null;
    private File file;
    private YamlConfiguration configuration;

    public HashMap<String, FastBoard> board;

    @Override
    public void onEnable() {

        INSTANCE = this;

        this.board = new HashMap<>();
        this.locationChestMap =  new HashMap<>();
        this.teamCuboidMap = new HashMap<>();
        this.teamDoorMap = new HashMap<>();
        this.uuidDecoGameManagerMap = new HashMap<>();

        //Commands
        getCommand("mc").setExecutor(new MCCommand());

        //Listeners
        getServer().getPluginManager().registerEvents(new JoinListener() ,this);
        getServer().getPluginManager().registerEvents(new InteractListener() ,this);
        getServer().getPluginManager().registerEvents(new MineurListener(), this);
        getServer().getPluginManager().registerEvents(new AgileListener(), this);
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new CuboidListener(), this);
        getServer().getPluginManager().registerEvents(new DoorListener(), this);
        getServer().getPluginManager().registerEvents(new DecoGameListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);

        //file
        load();

        //GameSettings
        this.gameSettings = new GameSettings(getConfig().getString("gametype"), getConfig().getInt("minPlayers"), getConfig().getInt("maxPlayers"), getConfig().getInt("countdownStartTimer"), new Location(Bukkit.getWorld(getConfig().getString("lobbySpawn.worldname")), getConfig().getDouble("lobbySpawn.x"), getConfig().getDouble("lobbySpawn.y"), getConfig().getDouble("lobbySpawn.z"), (float)getConfig().getDouble("lobbySpawn.yaw"), (float)getConfig().getDouble("lobbySpawn.pitch")), getConfig().getBoolean("useTeams"), getConfig().getInt("teamSize"), getConfig().getInt("teamsCount"), GameMode.valueOf(getConfig().getString("gamemode")), GameMode.valueOf(getConfig().getString("lobbymode")), GameMode.valueOf(getConfig().getString("spectatorMode")), getConfig().getInt("foodLevel"), getConfig().getDouble("healthLevel"));

        //TeamsManager
        this.teamsManager = new TeamsManager(this.gameSettings.getTeamsCount(), this.gameSettings.getTeamSize(), this.gameSettings.isUseTeams());

        //LobbyRunnable
        this.lobbyRunnable = new LobbyRunnable(false);

        //GameRunnable
        this.gameRunnable = new GameRunnable();

        //CassementManager
        this.classementManager = new ClassementManager(false);

        //GameState
        GameState.setState(GameState.LOBBY);

        //LoadConfiguration
        loadConfiguration();

        //WorldSetting(s)
        World world = Bukkit.getWorld(getConfig().getString("worldname"));
        world.getEntities().forEach(entity -> {
            entity.remove();
        });
    }

    public void load() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        file = new File(getDataFolder() + File.separator + "config.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        configuration  = YamlConfiguration.loadConfiguration(file);
    }

    public void loadConfiguration() {
        World world = Bukkit.getWorld(getConfig().getString("teams.red.spawn.worldname"));

        this.teamsManager.setTeamLocation(Teams.RED, Utils.parseStringToLoc(
                getConfig().getString("teams.red.spawn.pos")
                , world
                , (float) getConfig().getDouble("teams.red.spawn.yaw")
                , (float) getConfig().getDouble("teams.red.spawn.pitch")));

        this.teamsManager.setTeamLocation(Teams.BLUE, Utils.parseStringToLoc(
                getConfig().getString("teams.blue.spawn.pos")
                , world
                ,(float) getConfig().getDouble("teams.blue.spawn.yaw")
                ,(float) getConfig().getDouble("teams.blue.spawn.pitch")));

        this.teamsManager.setTeamLocation(Teams.YELLOW, Utils.parseStringToLoc(
                getConfig().getString("teams.yellow.spawn.pos")
                , world
                ,(float) getConfig().getDouble("teams.yellow.spawn.yaw")
                ,(float) getConfig().getDouble("teams.yellow.spawn.pitch")));

        this.teamsManager.setTeamLocation(Teams.DARK_GREEN, Utils.parseStringToLoc(
                getConfig().getString("teams.green.spawn.pos")
                , world
                ,(float) getConfig().getDouble("teams.green.spawn.yaw")
                ,(float) getConfig().getDouble("teams.green.spawn.pitch")));

        Cuboid cuboidr = new Cuboid(Utils.parseStringToLocCuboid(getConfig().getString("teams.red.protection.pos1")
                , world), Utils.parseStringToLocCuboid(getConfig().getString("teams.red.protection.pos2"), world));
        this.teamsManager.getTeamsCuboid().put(Teams.RED, cuboidr);

        Cuboid cuboidb = new Cuboid(Utils.parseStringToLocCuboid(getConfig().getString("teams.blue.protection.pos1")
                , world), Utils.parseStringToLocCuboid(getConfig().getString("teams.blue.protection.pos2"), world));
        this.teamsManager.getTeamsCuboid().put(Teams.BLUE, cuboidb);

        Cuboid cuboidy = new Cuboid(Utils.parseStringToLocCuboid(getConfig().getString("teams.yellow.protection.pos1")
                , world), Utils.parseStringToLocCuboid(getConfig().getString("teams.yellow.protection.pos2"), world));
        this.teamsManager.getTeamsCuboid().put(Teams.YELLOW, cuboidy);

        Cuboid cuboidg = new Cuboid(Utils.parseStringToLocCuboid(getConfig().getString("teams.green.protection.pos1")
                , world), Utils.parseStringToLocCuboid(getConfig().getString("teams.green.protection.pos2"), world));
        this.teamsManager.getTeamsCuboid().put(Teams.DARK_GREEN, cuboidg);
    }

    public static Main INSTANCE() {
        return INSTANCE;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public TeamsManager getTeamsManager() {
        return teamsManager;
    }

    public LobbyRunnable getLobbyRunnable() {
        return lobbyRunnable;
    }

    public void setLobbyRunnable(LobbyRunnable lobbyRunnable) {
        this.lobbyRunnable = lobbyRunnable;
    }

    public GameRunnable getGameRunnable() {
        return gameRunnable;
    }

    public HashMap<String, FastBoard> getBoard() {
        return board;
    }

    public ClassementManager getClassementManager() {
        return classementManager;
    }

    public void setClassementManager(ClassementManager classementManager) {
        this.classementManager = classementManager;
    }
}