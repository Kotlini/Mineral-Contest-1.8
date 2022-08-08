package fr.ylanouh.mineralcontest.game.kit;

import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Kit {

    //Mineur
    //Lorsque vous minez, les minerais sont déjà fondus mais vous avez une ligne d'inventaire en moins
    //Guerrier
    //Vous infligez 25% de dégàts supplémentaires mais vous n'avez que 7 ❤.
    //Travailleur
    //Vous augmentez les points gagnés par votre équipe de 25% mais vous n'aurez que 5 ❤.
    //Robuste
    //Vous avez 15 ❤ et vous prenez 15% de dégàts en moins.
    //Agile
    //Vous vous déplacez avec 20% de vitesse en plus et vous ne prenez pas de dégàts de chute.


    private static List<Player> mineurs = new ArrayList<>();
    private static List<Player> guerrier = new ArrayList<>();
    private static List<Player> travailleur = new ArrayList<>();
    private static List<Player> robuste = new ArrayList<>();
    private static List<Player> agile = new ArrayList<>();

    public static void addMineur(Player player) {
        mineurs.add(player);
    }

    public static void addGeurrier(Player player) {
        guerrier.add(player);
    }

    public static void addTravailleur(Player player) {
        travailleur.add(player);
    }

    public static void addRobuste(Player player) {
        robuste.add(player);
    }

    public static void addAgile(Player player) {
        agile.add(player);
    }

    public static void removeKit (Player player) {
        if (robuste.contains(player)) {
            robuste.remove(player);
        } else if (agile.contains(player)) {
            agile.remove(player);
        } else if (guerrier.contains(player)) {
            guerrier.remove(player);
        } else if (travailleur.contains(player)) {
            travailleur.remove(player);
        } else if (mineurs.contains(player)) {
            mineurs.remove(player);
        }
    }

    public static void startKitForAllPlayers() {
        for (Player p : mineurs) {
            for (int x = 9; x < 18; x++) {
                p.getInventory().setItem(x, new ItemStack(Material.BARRIER));
            }
        }

        for (Player p : agile) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        }

        for (Player p : travailleur) {
            Teams team = Main.INSTANCE().getTeamsManager().getPlayerTeam(p.getPlayer());

            if (Main.INSTANCE().getTeamsManager().getTeamBoost(team) == null) {
                Main.INSTANCE().getTeamsManager().setTeamBoost(team, 25);
            }else {
                Main.INSTANCE().getTeamsManager().setTeamBoost(team, Main.INSTANCE().getTeamsManager()
                        .getTeamBoost(team)+25);
            }
        }

        for (Player p : robuste) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
            p.setMaxHealth(30);
            p.setHealth(30);
        }

        for (Player p : guerrier) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
            p.setMaxHealth(14);
            p.setMaxHealth(14);
        }
    }

    public static boolean asKit(Player player) {
        if (agile.contains(player)) {
            return true;
        } else if (robuste.contains(player)) {
            return true;
        } else if (guerrier.contains(player)) {
            return true;
        } else if (mineurs.contains(player)) {
            return true;
        } else if (travailleur.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getKit(Player player) {
        if (agile.contains(player)) {
            return "Agile";
        } else if (robuste.contains(player)) {
            return "Robuste";
        } else if (guerrier.contains(player)) {
            return "Guerrier";
        } else if (mineurs.contains(player)) {
            return "Mineur";
        } else if (travailleur.contains(player)) {
            return "Travailleur";
        }
        return null;
    }

    public static void getHealTokit(Player player) {

        if (Kit.getKit(player).equalsIgnoreCase("Guerrier")) {
            player.setMaxHealth(14);
            player.setHealth(14);
        } else if (Kit.getKit(player).equalsIgnoreCase("Travailleur")) {
            player.setHealth(10);
            player.setMaxHealth(10);
        } else if (Kit.getKit(player).equalsIgnoreCase("Robuste")) {
            player.setHealth(30);
            player.setMaxHealth(30);
        }
    }

    public static void startKitPlayer(Player p) {
        if (mineurs.contains(p)) {
            for (int x = 9; x < 18; x++) {
                p.getInventory().setItem(x, ItemCreator.createItem(Material.BARRIER, "§cVous etes mineur", 1, (byte) 0, null, 0, "null", null));
            }
        } else if (travailleur.contains(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        } else if (robuste.contains(p)) {
            Teams team = Main.INSTANCE().getTeamsManager().getPlayerTeam(p.getPlayer());

            if (Main.INSTANCE().getTeamsManager().getTeamBoost(team) == 0 ||
                    Main.INSTANCE().getTeamsManager().getTeamBoost(team) == null) {
                Main.INSTANCE().getTeamsManager().setTeamBoost(team, 25);
            }else {
                Main.INSTANCE().getTeamsManager().setTeamBoost(team, Main.INSTANCE().getTeamsManager()
                        .getTeamBoost(team) + 25);
            }
        } else if (robuste.contains(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
            p.setMaxHealth(30);
            p.setHealth(30);
        } else if (guerrier.contains(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
            p.setMaxHealth(14);
            p.setMaxHealth(14);
        }
    }
}









