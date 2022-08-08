package fr.ylanouh.mineralcontest.game.death;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.kit.Kit;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathTimer extends BukkitRunnable {

    private int remainingDeathTime = 30;
    private Player player;

    public DeathTimer(Player player) {
        this.player = player;
        this.player.setGameMode(GameMode.SPECTATOR);
        this.player.getInventory().clear();
        this.player.getInventory().setHelmet(null);
        this.player.getInventory().setChestplate(null);
        this.player.getInventory().setLeggings(null);
        this.player.getInventory().setBoots(null);
        this.player.getActivePotionEffects().clear();
        this.player.setMaxHealth(20);
        this.player.setHealth(20);
        this.player.setLevel(0);
    }

    @Override
    public void run() {
        if (remainingDeathTime <= 0) {
            this.player.setGameMode(GameMode.SURVIVAL);
            this.player.teleport(Main.INSTANCE().getTeamsManager().getTeamLocation(
                    Main.INSTANCE().getTeamsManager().getPlayerTeam(this.player)));
            Utils.startPlayerEquipementTarget(player);
            Kit.startKitPlayer(this.player);
            this.cancel();
        }

        this.player.sendTitle(String.valueOf(this.getRemainingDeathTime()), ChatColor.RED + "Réaparition");

        this.remainingDeathTime--;
    }

    public int getRemainingDeathTime() {
        return this.remainingDeathTime;
    }

}