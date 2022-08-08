package fr.ylanouh.mineralcontest.game.runnables;

import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.kit.Kit;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class VerifPlayerGame extends BukkitRunnable {

    int timer = 5;
    boolean watting = false;

    @Override
    public void run() {
        if (Utils.playersIsValids()) {
            if (watting == false) {
                for (Player teamRouge : Main.INSTANCE().getTeamsManager().getTeamPlayerList(Teams.RED)) {
                    teamRouge.teleport(Main.INSTANCE().getTeamsManager().getTeamLocation(Teams.RED));
                }
                for (Player teamBlue : Main.INSTANCE().getTeamsManager().getTeamPlayerList(Teams.BLUE)) {
                    teamBlue.teleport(Main.INSTANCE().getTeamsManager().getTeamLocation(Teams.BLUE));
                }
                for (Player teamGreen : Main.INSTANCE().getTeamsManager().getTeamPlayerList(Teams.DARK_GREEN)) {
                    teamGreen.teleport(Main.INSTANCE().getTeamsManager().getTeamLocation(Teams.DARK_GREEN));
                }
                for (Player teamGreen : Main.INSTANCE().getTeamsManager().getTeamPlayerList(Teams.DARK_GREEN)) {
                    teamGreen.teleport(Main.INSTANCE().getTeamsManager().getTeamLocation(Teams.DARK_GREEN));
                }
                for (Player teamYellow : Main.INSTANCE().getTeamsManager().getTeamPlayerList(Teams.YELLOW)) {
                    teamYellow.teleport(Main.INSTANCE().getTeamsManager().getTeamLocation(Teams.YELLOW));
                }

                Utils.startPlayerEquipement();
                Kit.startKitForAllPlayers();
                watting = true;
            }

            if (timer == 0) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    player.sendTitle("§2§lBonne Chance", "");
                    GameState.setState(GameState.GAME);
                }
                Main.INSTANCE().getGameRunnable().runTaskTimerAsynchronously(Main.INSTANCE(), 0L, 20L);
                Main.INSTANCE().getGameRunnable().start = true;
                this.cancel();
                }

            if (timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1) {
                Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle("§cAttention","§6§l"+timer));
            }

            timer--;
        }
    }
}
