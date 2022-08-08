package fr.ylanouh.mineralcontest.game.animations;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.utils.AnimationUtils;
import fr.ylanouh.mineralcontest.utils.TitleManager;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chest {

    private Location chestLoc;

    private Inventory inv;

    private Player playerInAnim;

    private Phase phase;

    private boolean playerAnim;

    public Chest(Phase phase) {
        this.chestLoc = null;
        this.inv = null;
        this.playerInAnim = null;
        this.phase = phase;
        this.playerAnim = false;
    }


    public Inventory getInv() {
        return inv;
    }

    public Location getChestLoc() {
        return chestLoc;
    }

    public Phase getPhase() {
        return phase;
    }

    public Player getPlayerInAnim() {
        return playerInAnim;
    }

    public void setPlayerInAnim(Player playerInAnim) {
        this.playerInAnim = playerInAnim;
    }

    public void addEvent() {

        if (this.getPhase().equals(Phase.LARGAGE)) {
            if (AnimationUtils.chanceForSpawnLargage()) {
                this.inv = AnimationUtils.createRandomInv();
                this.chestLoc = AnimationUtils.createChestLargage();
                Main.INSTANCE().locationChestMap.put(this.chestLoc, this);

                String message = "§f§m------------- §6§l> Mineral Conteste < §f§m-----------\n" +
                        "§3§lUn largage est apparu sur la map\n" +
                        "§4Coordonnées: " +
                        "§cx: " + this.chestLoc.getX() + " y: " + this.chestLoc.getY() + " z: " +
                        this.chestLoc.getZ() + "\n" + "§f§m-------------------------------------------\n";

                Bukkit.getOnlinePlayers().forEach(players -> {

                    players.sendMessage(message);
                    players.sendTitle("§6§lUn Largage à spawn",
                            "§cx: " + this.chestLoc.getX() +
                                    " §cy: " + this.chestLoc.getY() +
                                    " §cz: "+ this.chestLoc.getZ());
                    players.playSound(players.getLocation(), Sound.ANVIL_USE, 10L, 1L);
                });
            }
            return;
        }

        if (getEvent(Utils.parseStringToLocCuboid(Main.INSTANCE().getConfig().getString("chestEventLoc"),
                Bukkit.getWorld("world"))) == null && !this.getPhase().equals(Phase.LARGAGE)) {

            this.inv = AnimationUtils.createRandomInv();
            this.chestLoc = AnimationUtils.createChestArena();
            Main.INSTANCE().locationChestMap.put(this.chestLoc, this);

            String message = "§f§m------------- §6§l> Mineral Conteste < §f§m-----------\n" +
                    "§3§lUn coffre est apparu dans l'arène\n" +
                    "§e/arene §8- §7pour y accedé plus rapidement\n" +
                    "§4!ATTENTION! §c- Tout les joueurs de votre\n" +
                    "§céquipe seront téléporté aussi\n" +
                    "§f§m-------------------------------------------\n";

            Bukkit.broadcastMessage(message);
            Bukkit.getOnlinePlayers().forEach(players -> {
                players.sendTitle(
                        "§6§lUn coffre est",
                        "§6§lapparu dans l'arène");
            });
        }
    }

    public void remove() {
        this.chestLoc.getBlock().setType(Material.AIR);
        Main.INSTANCE().locationChestMap.remove(this.chestLoc);
    }

    public static Chest getEvent(Location location) {
            return Main.INSTANCE().locationChestMap.get(location);
    }

    public static boolean isEventChestInArenaRun() {
        for (Chest chest : Main.INSTANCE().locationChestMap.values()) {
            if (chest.getPhase() != Phase.LARGAGE) {
                return true;
            }
        }
        return false;
    }

    public static Chest giveEventChestInArenaRun() {
        if (isEventChestInArenaRun()) {
            for (Chest chest : Main.INSTANCE().locationChestMap.values()) {
                if (chest.getPhase() != Phase.LARGAGE) {
                    return chest;
                }
            }
        }
        return null;
    }

    public static Chest giveEventLargage(Location location) {
            for (Chest chest : Main.INSTANCE().locationChestMap.values()) {
                if (chest.getPhase() == Phase.LARGAGE && chest.getChestLoc() == location) {
                    return chest;
                }
            }
        return null;
    }

    public static Chest getEventLargageByID(int ID) {
        List<Chest> ListID = new ArrayList<>();

        for (Chest chest : Main.INSTANCE().locationChestMap.values()) {
            if (chest.getPhase() == Phase.LARGAGE) {
                ListID.add(chest);
            }
        }

        if (ListID.get(ID-1) != null) {
            return ListID.get(ID-1);
        }
        return null;
    }

    public static Chest getEventByPlayer(Player player) {
        for (Chest chest : Main.INSTANCE().locationChestMap.values()) {
            if (chest.getPlayerInAnim() == player) {
                return chest;
            }
        }
        return null;
    }

    public boolean isPlayerAnim() {
        return playerAnim;
    }

    public void setPlayerAnim(boolean playerAnim) {
        this.playerAnim = playerAnim;
    }
}
