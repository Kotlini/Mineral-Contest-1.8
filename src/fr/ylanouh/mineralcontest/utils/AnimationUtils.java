package fr.ylanouh.mineralcontest.utils;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.animations.Chest;
import fr.ylanouh.mineralcontest.game.animations.Phase;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class AnimationUtils {

    public static Location createChestArena() {
        World world = Bukkit.getWorld(Main.INSTANCE().getConfig().getString("worldname"));
        Location chestLoc = Utils.parseStringToLocCuboid(
                Main.INSTANCE().getConfig().getString("chestEventLoc"),
                world);

        chestLoc.getBlock().setType(Material.CHEST);
        return chestLoc;
    }
    public static Location createChestLargage() {
        int max = Main.INSTANCE().getConfig().getInt("largage.max");
        int min = Main.INSTANCE().getConfig().getInt("largage.min");
        World world = Bukkit.getWorld(Main.INSTANCE().getConfig().getString("worldname"));
        Location center = Utils.parseStringToLocCuboid(
                Main.INSTANCE().getConfig().getString("chestEventLoc"),
                world);

        int x = new Random().nextInt(max - min) + min;
        int z = new Random().nextInt(max - min) + min;

        Location spawn = new Location(
                world,
                x + center.getX(),
                0,
                z + center.getZ()
        );

        spawn.setY(world.getHighestBlockYAt(spawn));
        Utils.isInWaterBlockAndCorrection(spawn);
        spawn.getBlock().setType(Material.CHEST);

        return spawn;
    }

    public static Inventory createRandomInv() {
        int nb = 0;
        Material mat = null;
        Inventory inv = Bukkit.createInventory(null, 9*3);

        for (int slot = 0; slot < 27; slot++) {
            int nbRand = (int) (Math.random() * 100);

            if (nbRand <= 30) {
                nb = randomNumberForIngot();
                mat = Material.IRON_INGOT;
            } else if (nbRand > 30 && nbRand <= 60) {
                nb = randomNumberForIngot();
                mat = Material.GOLD_INGOT;
            } else if (nbRand > 60 && nbRand <= 85) {
                nb = randomNumberForIngot();
                mat = Material.DIAMOND;
            } else if (nbRand > 85 && nbRand <= 100) {
                nb = randomNumberForIngot();
                mat = Material.EMERALD;
            }
            inv.setItem(slot, new ItemStack(mat, nb));
        }
        return inv;
    }

    public static int randomNumberForIngot() {
        int nbRand = (int) (Math.random() * 100);

        if (nbRand <= 25) {
            return 1;
        } else if (nbRand > 25 && nbRand <= 50) {
            return 2;
        } else if (nbRand > 50 && nbRand <= 75) {
            return 3;
        } else if (nbRand > 75 && nbRand <= 100) {
            return 4;
        }
        return 1;
    }

    public static boolean chanceForSpawnLargage() {
        int nbRand = (int) (Math.random() * 100);

        if (nbRand <= 25) {
            return true;
        }
        return false;
    }

    public static void addPlayerInAnimationArena(Chest event) {
        Inventory inv = Bukkit.createInventory(null, 3*9, "§3§lChargement...");
        Player player = event.getPlayerInAnim();
        new BukkitRunnable(){
            int timer = 5;
            @Override
            public void run() {

                if (event.getPlayerInAnim() == null) {
                    event.setPlayerAnim(false);
                    this.cancel();
                }

                if (timer == 5) {
                    for (int x = 0; x < 27; x++) {
                        if (x == 12 || x == 13 || x == 14 || x == 15) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0));
                        } else if (x == 11) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5));
                        } else {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
                        }
                    }
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 10L, 5L);
                    player.openInventory(inv);
                } else if (timer == 4) {
                    for (int x = 0; x < 27; x++) {
                        if (x == 13 || x == 14 || x == 15) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0));
                        } else if (x == 11 || x == 12) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5));
                        } else {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
                        }
                    }
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 10L, 5L);
                    player.openInventory(inv);
                } else if (timer == 3) {
                    for (int x = 0; x < 27; x++) {
                        if (x == 14 || x == 15) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0));
                        } else if (x == 11 || x == 12 || x == 13) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5));
                        } else {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
                        }
                    }
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 10L, 5L);
                    player.openInventory(inv);
                } else if (timer == 2) {
                    for (int x = 0; x < 27; x++) {
                        if (x == 15) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0));
                        } else if (x == 11 || x == 12 || x == 13 || x == 14) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5));
                        } else {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
                        }
                    }
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 10L, 5L);
                    player.openInventory(inv);
                } else if (timer == 1) {
                    for (int x = 0; x < 27; x++) {
                        if (x == 11 || x == 12 || x == 13 || x == 14 || x == 15) {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5));
                        } else {
                            inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
                        }
                    }
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 10L, 5L);
                    player.openInventory(inv);
                } else if (timer == 0) {
                    event.getPlayerInAnim().openInventory(event.getInv());
                    addPlayerInAnimationPickup(event);
                    this.cancel();
                }
                timer--;
            }
        }.runTaskTimer(Main.INSTANCE(), 0, 20);
    }

    public static void addPlayerInAnimationPickup(Chest event) {
        event.setPlayerAnim(false);
        Inventory inv = event.getInv();
        Player player = event.getPlayerInAnim();
        player.openInventory(inv);
        new BukkitRunnable(){
            @Override
            public void run() {
                for(ItemStack item : inv.getContents()) {
                    if (item instanceof ItemStack) {
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1.0f, 0.0f);
                        player.openInventory(inv);
                        inv.remove(item);
                    }
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                player.closeInventory();
                event.remove();

                if (event.getPhase() != Phase.LARGAGE) {
                    Bukkit.broadcastMessage(Main.INSTANCE().getGameSettings().getGametype().replaceAll("&", "§") +
                                    " §3§lLe coffre d'arène à été pris");
                    Bukkit.getOnlinePlayers().forEach(player1 -> {
                        player1.playSound(player1.getLocation(), Sound.ANVIL_BREAK, 10L, 1L);
                    });
                }else {
                    Bukkit.broadcastMessage(Main.INSTANCE().getGameSettings().getGametype().replaceAll("&", "§") +
                            " §3§lLe largage à été pris");
                }

                this.cancel();
            }
        }.runTaskTimer(Main.INSTANCE(), 0, 20);
    }
}
