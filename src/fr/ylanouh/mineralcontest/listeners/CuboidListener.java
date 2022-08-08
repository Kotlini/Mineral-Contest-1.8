package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.cuboid.CuboidManager;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CuboidListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (GameState.isState(GameState.GAME)) {
            if (event.getClickedBlock() == null) return;
            Player player = event.getPlayer();
            Location location = event.getClickedBlock().getLocation();
            Teams team = Main.INSTANCE().getTeamsManager().getPlayerTeam(player);

            if (CuboidManager.getTeamToLoc(location) != null) {
                CuboidManager cuboidManager = CuboidManager.getTeamToLoc(location);
                if (cuboidManager.getTeam() != team) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Location location = event.getBlock().getLocation();

        if (CuboidManager.getTeamToLoc(location) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();

        if (CuboidManager.getTeamToLoc(location) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        Location location = event.getEntity().getLocation();

        if (CuboidManager.getTeamToLoc(location) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplodeBlock(BlockExplodeEvent event) {
        Location location = event.getBlock().getLocation();

        if (CuboidManager.getTeamToLoc(location) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPortal(EntityCreatePortalEvent event) {
        event.setCancelled(true);
    }
}
