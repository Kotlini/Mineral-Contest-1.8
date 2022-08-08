package fr.ylanouh.mineralcontest.utils;


import org.bukkit.Material;

public class RawToCooked {
    public static Material toCooked(Material material) {
        switch (material) {
            case GOLD_ORE:
                return Material.GOLD_INGOT;
            case IRON_ORE:
                return Material.IRON_INGOT;
            case RAW_BEEF:
                return Material.COOKED_BEEF;
            case RAW_CHICKEN:
                return Material.COOKED_CHICKEN;
            case RABBIT:
                return Material.COOKED_RABBIT;
            case RAW_FISH:
                return Material.COOKED_FISH;
            default:
                return null;
        }
    }
}
