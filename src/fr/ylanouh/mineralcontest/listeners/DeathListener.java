package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.death.DeathTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitTask;

public class DeathListener implements Listener {

    @EventHandler
    public void Death(PlayerDeathEvent event) {
        DeathTimer deathTimer = new DeathTimer(event.getEntity());
        deathTimer.runTaskTimer(Main.INSTANCE(), 0, 20);
    }
}
