package fr.ylanouh.mineralcontest.utils;

import fr.ylanouh.mineralcontest.game.classement.ClassementEnum;
import fr.ylanouh.mineralcontest.game.kit.Kit;
import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.scoreboard.FastBoard;
import fr.ylanouh.mineralcontest.game.scoreboard.ScoreboardDispatch;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.item.ItemCreator;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    public static boolean playersIsValids() {
        int nb = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Kit.asKit(player)) {
                if (Main.INSTANCE().getTeamsManager().hasTeamPlayer(player)) {
                    nb++;
                    if (nb == Bukkit.getOnlinePlayers().size()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void sendMessageAllPlayers(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendRawMessage(message);

        }
    }

    public static void resetPlayersKit(Player player) {
        player.setMaxHealth(20);
        player.setHealth(20);
        player.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(player::removePotionEffect);
        player.getInventory().clear();
    }

    public static String changeFormatInt(String format, int timer) {
        if (timer < 60) {
            return new SimpleDateFormat(format).format(new Date(timer * 1000)) + " s";
        }else if (timer >= 60 && timer < 3600) {
            return new SimpleDateFormat(format).format(new Date(timer * 1000)) + " m";
        }else if (timer >= 3600) {
            return new SimpleDateFormat(format).format(new Date(timer * 1000)) + " h";
        }
        System.out.println("§cLe format ou le timer est incompatible.");
        return "Error: check console";
    }

    public static Location parseStringToLoc(String loc, World world, float yaw, float pitch) {

        String[] parts = loc.split(",");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location parseStringToLocCuboid(String loc, World world) {

        String[] parts = loc.split(",");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);

        return new Location(world, x, y, z);
    }

    public static String parseLocToString(Location loc) {
        return loc.getX()+","+loc.getY()+","+loc.getZ();
    }

    public static String getDate() {
        Date date = new Date();
        String days = date.getDate()+"";
        String months = (date.getMonth()+1)+"";
        String hours = date.getHours()+"";
        String minutes = date.getMinutes()+"";
        String secondes = date.getSeconds()+"";


        if(days.length()==1) days = "0"+days;
        if(months.length()==1) months = "0"+months;
        if(hours.length()==1) hours = "0"+hours;
        if(minutes.length()==1) minutes = "0"+minutes;
        if(secondes.length()==1) secondes = "0"+secondes;
        String d = days+"/"+months+"/"+(date.getYear()-100);
        return d;

    }

    public static void startPlayerEquipement() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getInventory().clear();
            player.setLevel(0);
            player.getActivePotionEffects().clear();
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1));
            player.getInventory().setItem(0, ItemCreator.createItem(Material.IRON_SWORD, null, 1, (byte) 0, null, 0, null, null));
            player.getInventory().setItem(1, ItemCreator.createItem(Material.COOKED_BEEF, null, 64, (byte) 0, null, 0, null, null));
            player.getInventory().setItem(2, ItemCreator.createItem(Material.STONE_AXE, null, 1, (byte) 0, null, 0, null, null));
            player.getInventory().setItem(3, ItemCreator.createItem(Material.IRON_PICKAXE, null, 1, (byte) 0, Enchantment.DIG_SPEED, 3, null, null));

            player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
            player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));

            Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.RED, 0);
            Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.YELLOW, 0);
            Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.BLUE, 0);
            Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.DARK_GREEN, 0);
            Main.INSTANCE().getTeamsManager().setTeamBoost(Teams.RED, 0);
            Main.INSTANCE().getTeamsManager().setTeamBoost(Teams.YELLOW, 0);
            Main.INSTANCE().getTeamsManager().setTeamBoost(Teams.BLUE, 0);
            Main.INSTANCE().getTeamsManager().setTeamBoost(Teams.DARK_GREEN, 0);
        });

        for (FastBoard board : Main.INSTANCE().getBoard().values()) {
            ScoreboardDispatch.updateBoardGame(board);
        }
    }

    public static void startPlayerEquipementTarget(Player player) {

            player.getInventory().clear();
            player.setLevel(0);
            player.getActivePotionEffects().clear();
            player.getInventory().setItem(0, ItemCreator.createItem(Material.IRON_SWORD, null, 1, (byte) 0, null, 0, null, null));
            player.getInventory().setItem(1, ItemCreator.createItem(Material.COOKED_BEEF, null, 64, (byte) 0, null, 0, null, null));
            player.getInventory().setItem(2, ItemCreator.createItem(Material.STONE_AXE, null, 1, (byte) 0, null, 0, null, null));
            player.getInventory().setItem(3, ItemCreator.createItem(Material.IRON_PICKAXE, null, 1, (byte) 0, Enchantment.DIG_SPEED, 3, null, null));

            player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
            player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));


            for (FastBoard board : Main.INSTANCE().getBoard().values()) {
                ScoreboardDispatch.updateBoardGame(board);
            }
    }

    public static Location add(Location location, int x, int y, int z) {
        return new Location(location.getWorld(), location.getX()+x, location.getY()+y, location.getZ()+z);
    }

    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<ItemStack> transfereInv(Player player) {
        List<ItemStack> stackList = new ArrayList<>();

        for (ItemStack it : player.getInventory()) {
            if (!it.getType().equals(Material.BARRIER)) {
               stackList.add(it);
            }
        }
        return stackList;
    }


    public static void setArmorstandStyle(Teams team, ArmorStand armorStand) {
        if (team.getClassementEnum().equals(ClassementEnum.PREMIER)) {
            armorStand.setBasePlate(false);
            armorStand.setGravity(false);
            armorStand.setVisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(team.getName());
            armorStand.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            armorStand.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            armorStand.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            armorStand.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        } else if (team.getClassementEnum().equals(ClassementEnum.DEUXIEME)) {
            armorStand.setBasePlate(false);
            armorStand.setGravity(false);
            armorStand.setVisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(team.getName());
            armorStand.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
            armorStand.setBoots(new ItemStack(Material.GOLD_BOOTS));
            armorStand.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            armorStand.setHelmet(new ItemStack(Material.GOLD_HELMET));
        } else if (team.getClassementEnum().equals(ClassementEnum.TROISIEME)) {
            armorStand.setBasePlate(false);
            armorStand.setGravity(false);
            armorStand.setVisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(team.getName());
            armorStand.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            armorStand.setBoots(new ItemStack(Material.IRON_BOOTS));
            armorStand.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            armorStand.setHelmet(new ItemStack(Material.IRON_HELMET));
        } else if (team.getClassementEnum().equals(ClassementEnum.QUATRIEME)) {
            armorStand.setBasePlate(false);
            armorStand.setGravity(false);
            armorStand.setVisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(team.getName());
            armorStand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            armorStand.setBoots(new ItemStack(Material.LEATHER_BOOTS));
            armorStand.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            armorStand.setHelmet(new ItemStack(Material.LEATHER_HELMET));
        }
    }

    public static void isInWaterBlockAndCorrection(Location loc) {
        while (true) {
            if (loc.getBlock().getType().equals(Material.WATER)) {
                loc.setY(loc.getY()+1);
            }else {
                return;
            }
        }
    }
}
