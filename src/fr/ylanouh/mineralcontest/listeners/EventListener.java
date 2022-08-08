package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.animations.Chest;
import fr.ylanouh.mineralcontest.game.animations.Phase;
import fr.ylanouh.mineralcontest.utils.AnimationUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class EventListener implements Listener {

    @EventHandler
    public void onInteractEventChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action ac = event.getAction();
        Block block = event.getClickedBlock();

        if (block == null) return;

        if (ac == Action.RIGHT_CLICK_BLOCK) {
            if (block.getType().equals(Material.CHEST)) {
                if (Chest.getEvent(block.getLocation()) != null) {
                    event.setCancelled(true);
                    Chest chest = Chest.getEvent(block.getLocation());

                    if(chest.getPlayerInAnim() != null) {
                        player.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replace(
                                "&", "§")
                                + " §cMhh... Il y a déjà un joueur qui prend ce coffre. §4§lDéfonce le !");
                        return;
                    }

                    if (chest.getPhase() != Phase.LARGAGE) {
                        event.setCancelled(true);
                        chest.setPlayerInAnim(player);
                        AnimationUtils.addPlayerInAnimationArena(chest);
                    }else {
                        chest.setPlayerInAnim(player);
                        AnimationUtils.addPlayerInAnimationPickup(chest);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            if (Chest.getEventByPlayer(player) != null) {
                Chest chest = Chest.getEventByPlayer(player);
                if (chest.getPhase() != Phase.LARGAGE) {
                    chest.setPlayerInAnim(null);
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();

        if (Chest.getEventByPlayer(player) != null) {
            event.setCancelled(true);
        }
    }
}
