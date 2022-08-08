package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class AgileListener implements Listener {

    @EventHandler
    public void fether(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (GameState.isState(GameState.GAME)) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (Kit.getKit(player).equalsIgnoreCase("Agile")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

