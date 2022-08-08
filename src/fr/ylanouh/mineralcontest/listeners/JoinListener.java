package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.game.GamePlayer;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.decogame.DecoGameManager;
import fr.ylanouh.mineralcontest.game.scoreboard.FastBoard;
import fr.ylanouh.mineralcontest.game.scoreboard.ScoreboardDispatch;
import fr.ylanouh.mineralcontest.utils.TitleManager;
import fr.ylanouh.mineralcontest.utils.Utils;
import fr.ylanouh.mineralcontest.item.ItemCreator;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class JoinListener implements Listener {
    private final Map<Player, Location> playerDisconnect = new HashMap<>();


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        GamePlayer gamePlayer;
        FastBoard board;

        if (Main.INSTANCE().getLobbyRunnable().isStarted()) {
            board = new FastBoard(player);
            board.updateTitle("§8» §9§lMineral Contest §8«");
            ScoreboardDispatch.updateBoardStarting(board);
        }else {
            board = new FastBoard(player);
            board.updateTitle("§8» §9§lMineral Contest §8«");
            ScoreboardDispatch.updateBoardWatting(board);
        }

        Main.INSTANCE().getBoard().put(player.getName(), board);

        if (GamePlayer.getGamePlayer(player.getUniqueId()) == null) {
            gamePlayer = new GamePlayer(player.getUniqueId(), board);
            gamePlayer.addPlayer();
        }else {
            gamePlayer = GamePlayer.getGamePlayer(player.getUniqueId());
            gamePlayer.setFastboard(board);
        }


        e.setJoinMessage(null);
        player.setGameMode(Main.INSTANCE().getGameSettings().getGamemode());
        String world = Main.INSTANCE().getConfig().getString("lobbySpawn.worldname");
        Bukkit.getWorld(world).setTime(6000L);
        Bukkit.getWorld(world).setStorm(false);
        player.setWalkSpeed(0.2F);
        if (GameState.isState(GameState.LOBBY)) {
            Bukkit.getOnlinePlayers().forEach(players -> TitleManager.sendActionBar(players, player.getName()+ " "));
            player.setFoodLevel(Main.INSTANCE().getGameSettings().getFoodLevel());
            player.setMaxHealth(Main.INSTANCE().getGameSettings().getHealthLevel());
            player.setHealth(Main.INSTANCE().getGameSettings().getHealthLevel());
            player.teleport(Main.INSTANCE().getGameSettings().getLobbySpawn());
            player.setLevel(0);
            player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
            player.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(player::removePotionEffect);
            player.getInventory().clear();
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setBoots(new ItemStack(Material.AIR));

            player.getInventory().setItem(4, ItemCreator.createItem(Material.CHEST, "§9§lEquipe", 1, 0, null, 0, null, null));
            player.getInventory().setItem(6, ItemCreator.createItem(Material.CHEST, "§3§lKit", 1, 0, null, 0, null, null));

            if (player.hasPermission("mineralcontest.admin.start")) {
                player.getInventory().setItem(0, ItemCreator.createItem(Material.DIAMOND, "§c§lStart", 1, 0, null, 0, null, null));
                player.getInventory().setItem(1, ItemCreator.createItem(Material.FEATHER, "§2§lSkip time", 1, 0, null, 0, null, null));

            }

            if (!Main.INSTANCE().getLobbyRunnable().isStarted() && Main.INSTANCE().getGameSettings().getMinPlayers() <= Bukkit.getOnlinePlayers().size()) {
                Main.INSTANCE().getLobbyRunnable().setStarted(true);
                Main.INSTANCE().getLobbyRunnable().runTaskTimerAsynchronously(Main.INSTANCE(), 0L, 20L);
            }

            Main.INSTANCE().getTeamsManager().removePlayerFromAll(player);

        } else if (GameState.isState(GameState.GAME) && DecoGameManager.getDecoGameManager(player) == null) {
            player.setGameMode(Main.INSTANCE().getGameSettings().getSpectatorMode());
            player.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replace(
                    "&", "§") +
                    " §7vous avez rejoint la partie en mode spectateur.");
            Main.INSTANCE().getTeamsManager().setSpectator(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        GamePlayer gamePlayer = GamePlayer.getGamePlayer(player.getUniqueId());
        e.setQuitMessage(null);
        if (GameState.isState(GameState.LOBBY) && GameState.isState(GameState.END)) {
            Bukkit.getOnlinePlayers().forEach(players -> TitleManager.sendActionBar(players, player.getName() + " §cquitté la partie ! §8(§9" + Bukkit.getOnlinePlayers() + "§8/§7" + Main.INSTANCE().getGameSettings().getMaxPlayers() + "§8)"));
            gamePlayer.remove(player.getUniqueId());
        } else if (GameState.isState(GameState.GAME)) {
            if (Main.INSTANCE().getConfig().getBoolean("decoGame")) {

            }

        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        GamePlayer gamePlayer = GamePlayer.getGamePlayer(player.getUniqueId());
        String message = event.getMessage();
        event.setCancelled(true);
        switch (GameState.getGameState()) {
            case LOBBY:
                Bukkit.broadcastMessage(player.getName() + ": " + event.getMessage());
                break;
            case END:
                Bukkit.broadcastMessage(player.getName() + ": " + event.getMessage());
                break;
            case GAME:
                if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    for (Player playerOnline : Bukkit.getOnlinePlayers()) {
                        if (playerOnline.getGameMode().equals(GameMode.SPECTATOR))
                            playerOnline.sendMessage(player.getName() + ": " + event.getMessage());
                    }
                    return;
                }else {
                    Bukkit.broadcastMessage(player.getName() + ": " + event.getMessage());
                }
                if (Main.INSTANCE().getGameSettings().isUseTeams()) {
                    if (message.startsWith("@") || message.startsWith("!")) {
                        Bukkit.broadcastMessage(gamePlayer.getTeams().getPrefix() + player.getName() + ": " + message.substring(1));
                        break;
                    }
                    for (Player playerOnline : Bukkit.getOnlinePlayers()) {
                        if (Main.INSTANCE().getTeamsManager().isInTeamPlayer(playerOnline, gamePlayer.getTeams()))
                            playerOnline.sendMessage("[" + gamePlayer
                                    .getTeams().getPrefix() + "] " + player
                                    .getName() + ": "+message);
                    }
                    break;
                }
                break;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (GameState.isState(GameState.LOBBY) || GameState.isState(GameState.END))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (GameState.isState(GameState.LOBBY) || GameState.isState(GameState.END))
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (GameState.isState(GameState.LOBBY) || GameState.isState(GameState.END))
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (GameState.isState(GameState.LOBBY) || GameState.isState(GameState.END))
            e.setCancelled(true);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        e.setMaxPlayers(Main.INSTANCE().getGameSettings().getMaxPlayers());
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState() == false) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChangeFood(FoodLevelChangeEvent event) {
        if (GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
        }
    }
}
