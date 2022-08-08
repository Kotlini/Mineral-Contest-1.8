package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.kit.Kit;
import fr.ylanouh.mineralcontest.utils.RawToCooked;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class MineurListener implements Listener {

    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        for (int x = 9; x < 18; x++) {
            if (event.getSlot() == x) {
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }

    @EventHandler
    public void onBlockDestroyed(BlockBreakEvent event) {

        // Si le joueur n'utilise pas ce kit, on s'arrête
        if (!Kit.getKit(event.getPlayer()).equalsIgnoreCase("Mineur")) return;

        Block blockDetruit = event.getBlock();
        Material materialToDrop = RawToCooked.toCooked(blockDetruit.getType());

        if (materialToDrop != null) {
            // On donne l'xp du bloc
            event.getPlayer().giveExp(new Random().nextInt(5));

            // On joue le son de l'xp
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ORB_PICKUP, 0.4f, 1);

            // On supprime le block
            blockDetruit.setType(Material.AIR);
            blockDetruit.getWorld().dropItemNaturally(blockDetruit.getLocation(), new ItemStack(materialToDrop));
        }
    }
}
