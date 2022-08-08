package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.door.Door;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class DoorListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Main.INSTANCE().getTeamsManager().getPlayerTeam(player) == null) return;
        if (!GameState.isState(GameState.GAME)) return;
        if (player.getGameMode().equals(GameMode.SPECTATOR)) return;

        Door door = Door.getDoor(player);
        door.getPlayerInDoor(player);
        door.getPlayerOutDoor(player);
    }
}
