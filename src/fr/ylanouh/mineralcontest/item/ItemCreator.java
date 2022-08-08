package fr.ylanouh.mineralcontest.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {

    public static ItemStack createItem(Material mat, String name, int nb, int byt, Enchantment ench, int in, String lor1, String lor2) {
        List<String> lore = new ArrayList<String>();
        lore.add(lor1);
        lore.add(lor2);

        ItemStack stack = new ItemStack(mat, nb, (byte) byt);
        ItemMeta meta = stack.getItemMeta();
        if (ench != null) {
            meta.addEnchant(ench, in, true);
        }
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
