package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.decogame.DecoGameManager;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DecoGameListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (GameState.isState(GameState.GAME)) {
            if (DecoGameManager.getDecoGameManager(player) != null) {
                DecoGameManager decoGameManager = DecoGameManager.getDecoGameManager(player);

                if (decoGameManager.getDecoGame().isEntityIsDead()) {
                    Utils.startPlayerEquipementTarget(player);
                    player.teleport(decoGameManager.getDecoGame().getLocPlayer());
                }
                decoGameManager.getDecoGame().setReco(true);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (GameState.isState(GameState.GAME)) {
            new DecoGameManager(player);
        }
    }

    @EventHandler
    public void onInteract(EntityInteractEvent event) {
        if (DecoGameManager.getDecoGameEntity(event.getEntity()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onKillEntity(EntityDeathEvent event) {
        if (DecoGameManager.getDecoGameEntity(event.getEntity()) != null) {
            DecoGameManager decoGameManager = DecoGameManager.getDecoGameEntity(event.getEntity());
            decoGameManager.dropInvOnKillEntity(event.getEntity().getKiller());
        }
    }
}
