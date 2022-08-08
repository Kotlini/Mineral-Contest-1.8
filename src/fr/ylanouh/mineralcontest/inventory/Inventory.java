package fr.ylanouh.mineralcontest.inventory;

import fr.ylanouh.mineralcontest.item.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Inventory {

    public static void openTeamInv(Player player) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 9*3, "§9Equipe");

        inv.setItem(10, ItemCreator.createItem(Material.BANNER, "§cRouge", 1, 0, null, 0, null, null));
        inv.setItem(12, ItemCreator.createItem(Material.BANNER, "§9Bleu", 1, 0, null, 0, null, null));
        inv.setItem(14, ItemCreator.createItem(Material.BANNER, "§2Verte", 1, 0, null, 0, null, null));
        inv.setItem(16, ItemCreator.createItem(Material.BANNER, "§eJaune", 1, 0, null, 0, null, null));

        player.openInventory(inv);
    }

    public static void openKitInv(Player player) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 9*3, "§3Kit");

        inv.setItem(10, ItemCreator.createItem(Material.FEATHER, "§3§lAgile", 1, 0, null, 0, null, "§fVous vous déplacez avec §420% §fde vitesse en plus et vous ne prenez pas de dégàts de chute."));
        inv.setItem(11, ItemCreator.createItem(Material.DIAMOND_CHESTPLATE, "§3§lRobuste", 1, 0, null, 0, null, "§fVous avez §515 §c❤ §fet vous prenez §415% §fde dégàts en moins."));
        inv.setItem(13, ItemCreator.createItem(Material.GOLD_INGOT, "§3§lTravailleur", 1, 0, null, 0, null, "§fVous augmentez les points gagnés par votre équipe de §425% §fmais vous n'aurez que §55 §c❤§f."));
        inv.setItem(14, ItemCreator.createItem(Material.DIAMOND_PICKAXE, "§3§lMineur", 1, 0, null, 0, null, "§fLorsque vous minez, les minerais sont déjà fondus mais vous avez une ligne d'inventaire en moins."));
        inv.setItem(15, ItemCreator.createItem(Material.DIAMOND_SWORD, "§3§lGuerrier", 1, 0, null, 0, null, "§fVous infligez §425% §fde dégàts supplémentaires mais vous n'avez que §57 §c❤§f."));

        player.openInventory(inv);
    }
}
