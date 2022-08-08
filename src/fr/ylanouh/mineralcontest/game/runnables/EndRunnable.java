package fr.ylanouh.mineralcontest.game.runnables;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class EndRunnable extends BukkitRunnable {

    @Override
    public void run() {
        int timer = 5;
        if (timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1 || timer == 0) {
            Bukkit.broadcastMessage("Reset dans: " + timer);
        }

        if (timer == 0 || Bukkit.getOnlinePlayers().size() == 0) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.kickPlayer("§2Reset in progress please wait...");
            });
            Bukkit.getConsoleSender().sendMessage("rl");
            Bukkit.getConsoleSender().sendMessage("reload confirm");
            this.cancel();
        }
        timer--;
    }
}
