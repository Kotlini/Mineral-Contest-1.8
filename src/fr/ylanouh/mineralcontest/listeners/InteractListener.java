package fr.ylanouh.mineralcontest.listeners;

import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.cuboid.CuboidManager;
import fr.ylanouh.mineralcontest.game.kit.Kit;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.inventory.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;

import java.util.ArrayList;
import java.util.List;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = event.getItem();
        Action action = event.getAction();

        if (stack == null) return;
        if (stack.getItemMeta() == null) return;

        if (GameState.isState(GameState.LOBBY) || GameState.isState(GameState.WAITTING)) {
            if (action  == Action.RIGHT_CLICK_AIR) {
                if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§9§lEquipe")) {
                    Inventory.openTeamInv(player);
                } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lKit")) {
                    Inventory.openKitInv(player);
                } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lStart")) {
                    player.chat("/start");
                } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§2§lSkip time")) {
                    player.chat("/start force");
                }
            }
        }
    }

    @EventHandler
    public void onClickInvTeamsChoice(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack stack = event.getCurrentItem();
        org.bukkit.inventory.Inventory inv = event.getInventory();

        if (stack == null) return;
        if (stack.getItemMeta() == null) return;

        if (GameState.isState(GameState.LOBBY) || GameState.isState(GameState.WAITTING)) {
            if (inv.getName().equalsIgnoreCase("§9Equipe")) {
                if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§cRouge")) {
                    Main.INSTANCE().getTeamsManager().removePlayerFromAll(player);
                    Main.INSTANCE().getTeamsManager().addPlayerTeam(player, Teams.RED);
                } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§9Bleu")) {
                    Main.INSTANCE().getTeamsManager().removePlayerFromAll(player);
                    Main.INSTANCE().getTeamsManager().addPlayerTeam(player, Teams.BLUE);
                } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§2Verte")) {
                    Main.INSTANCE().getTeamsManager().removePlayerFromAll(player);
                    Main.INSTANCE().getTeamsManager().addPlayerTeam(player, Teams.DARK_GREEN);
                } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§eJaune")) {
                    Main.INSTANCE().getTeamsManager().removePlayerFromAll(player);
                    Main.INSTANCE().getTeamsManager().addPlayerTeam(player, Teams.YELLOW);
                }else {
                    return;
                }
                player.updateInventory();
                player.closeInventory();
                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replace("&", "§") + " §aVous avez choisi l'équipe: " + Main.INSTANCE().getTeamsManager().getPlayerTeam(player).getName());
            }
        }
    }

    @EventHandler
    public void onClickInvKitChoice(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack stack = event.getCurrentItem();
        org.bukkit.inventory.Inventory inv = event.getInventory();

        if (stack == null) return;
        if (stack.getItemMeta() == null) return;

        if (inv.getName().equalsIgnoreCase("§3Kit")) {
            if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lAgile")) {
                Kit.removeKit(player);
                Kit.addAgile(player);
            } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lRobuste")) {
                Kit.removeKit(player);
                Kit.addRobuste(player);
            } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lTravailleur")) {
                Kit.removeKit(player);
                Kit.addTravailleur(player);
            } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lMineur")) {
                Kit.removeKit(player);
                Kit.addMineur(player);
            } else if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lGuerrier")) {
                Kit.removeKit(player);
                Kit.addGeurrier(player);
            }
            player.updateInventory();
            player.closeInventory();
            player.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replace("&", "§") + " §aVous avez choisi le kit: §3" + Kit.getKit(player));

        }
    }

    @EventHandler
    public void onClickEnder(PlayerInteractEvent event) {
        if (!GameState.isState(GameState.GAME)) return;
        if (event.getClickedBlock() == null) return;

        if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
            Player player = event.getPlayer();
            Location loc = event.getClickedBlock().getLocation();

            if (CuboidManager.getTeamToLoc(loc) != null) {
                Teams team = Main.INSTANCE().getTeamsManager().getPlayerTeam(player);
                CuboidManager cuboidManager = CuboidManager.getTeamToLoc(loc);
                if (cuboidManager.getTeam() == team) {
                    event.setCancelled(true);
                    player.openInventory(Bukkit.createInventory(null, 9*3, team.getName()));
                }
            }
        }
    }

    @EventHandler
    public void onCloseEnder(InventoryCloseEvent event) {
        if (!GameState.isState(GameState.GAME)) return;

        if (event.getPlayer() instanceof Player) {
            if (event.getInventory().getName().equalsIgnoreCase(
                    Main.INSTANCE().getTeamsManager().getPlayerTeam((Player) event.getPlayer()).getName())) {

                int pointsTotal;
                int emerald = 0;
                int diamond = 0;
                int gold = 0;
                int iron = 0;
                int redstone = 0;

                        for(ItemStack itemStack : event.getInventory().getContents()) {
                            if(itemStack != null) {
                                if (itemStack.getType().equals(Material.EMERALD)) {
                                    emerald = itemStack.getAmount() * 300;
                                } else if(itemStack.getType().equals(Material.DIAMOND)) {
                                    diamond = itemStack.getAmount() * 100;
                                } else if(itemStack.getType().equals(Material.GOLD_INGOT)) {
                                    gold = itemStack.getAmount() * 50;
                                } else if(itemStack.getType().equals(Material.IRON_INGOT)) {
                                    iron = itemStack.getAmount() * 10;
                                } else if(itemStack.getType().equals(Material.REDSTONE)) {
                                    redstone = itemStack.getAmount() * 3;
                                }
                            }

                        }

                pointsTotal = emerald + diamond + gold + iron;

                if (pointsTotal == 0 && redstone == 0) return;

                Teams team = Main.INSTANCE().getTeamsManager().getPlayerTeam((Player) event.getPlayer());

                Main.INSTANCE().getTeamsManager().setTeamPoints(team,
                        Main.INSTANCE().getTeamsManager().getTeamPoints(team)+pointsTotal);

                Main.INSTANCE().getTeamsManager().removePointsAllTeams(redstone, team);
                Player player = (Player) event.getPlayer();
                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replace(
                        "&", "§") +
                        " §3Vous avez gagné un total de §c§l" + pointsTotal +
                        " §cpoint(s) §3pour votre équipe et fait perdre aux adversaires §c§l" + redstone +
                        " §cpoint(s)");
            }
        }
    }
}
