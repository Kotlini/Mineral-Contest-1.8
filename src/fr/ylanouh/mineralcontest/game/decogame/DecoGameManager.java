package fr.ylanouh.mineralcontest.game.decogame;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class DecoGameManager {

    private DecoGame decoGame;

    public DecoGameManager(Player player) {
        BukkitTask task = new BukkitRunnable(){
            int timer = 0;
            @Override
            public void run() {
                if (decoGame.isReco()) {
                    Bukkit.getOnlinePlayers().forEach(player1 -> {
                        player1.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replaceAll("&", "§")+"§b§l"+player.getName()+" §3viens de se reconnecter en §c§l" + Utils.changeFormatInt("mm:ss", timer));
                    });
                    decoGame.getEntity().remove();
                    remove();
                    this.cancel();
                }

                if (decoGame.getTimer() == timer) {
                    remove();
                    this.cancel();
                }
                timer++;
            }
        }.runTaskTimer(Main.INSTANCE(), 0, 20);

        Entity png = player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        png.setCustomNameVisible(true);
        png.setCustomName(player.getName());

        this.decoGame = new DecoGame(player.getLocation(), player, task, player.getInventory(), png);
        Main.INSTANCE().uuidDecoGameManagerMap.put(player.getUniqueId(), this);

        transfereInvPlayer();

        Bukkit.getOnlinePlayers().forEach(player1 -> {
            player1.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replaceAll("&", "§")+" §b§l"+player.getName()+" §3viens de se deconnecter il y §c§l" + Utils.changeFormatInt("mm:ss", decoGame.getTimer())  + " §3pour se reconnecter");
        });
    }


    public DecoGame getDecoGame() {
        return decoGame;
    }

    public void remove() {
        Main.INSTANCE().uuidDecoGameManagerMap.remove(this.decoGame.getPlayer().getUniqueId());
    }

    public void dropInvOnKillEntity(Player killer) {
        this.decoGame.setEntityIsDead(true);
        for (ItemStack itemStack : this.decoGame.getInventory().getContents()) {
            if (itemStack != null) {
                this.decoGame.getEntity().getLocation().getWorld().dropItem(this.decoGame.getEntity().getLocation(),
                        itemStack);
            }
        }
    }

    public static DecoGameManager getDecoGameManager(Player player) {
        if (Main.INSTANCE().uuidDecoGameManagerMap.get(player.getUniqueId()) != null) {
            return Main.INSTANCE().uuidDecoGameManagerMap.get(player.getUniqueId());
        }
        return null;
    }

    public Inventory transfereInvPlayer() {
        Inventory inv = this.decoGame.getPlayer().getInventory();

        for(ItemStack itemStack : inv.getContents()) {
            if(itemStack != null) {
                if (itemStack.getType().equals(Material.EMERALD)) {
                    this.decoGame.getInventory().addItem(itemStack);
                } else if(itemStack.getType().equals(Material.DIAMOND)) {
                    this.decoGame.getInventory().addItem(itemStack);
                } else if(itemStack.getType().equals(Material.GOLD_INGOT)) {
                    this.decoGame.getInventory().addItem(itemStack);
                } else if(itemStack.getType().equals(Material.IRON_INGOT)) {
                    this.decoGame.getInventory().addItem(itemStack);
                } else if(itemStack.getType().equals(Material.REDSTONE)) {
                    this.decoGame.getInventory().addItem(itemStack);
                } else if (itemStack.getType().equals(Material.GOLD_ORE)) {
                    this.decoGame.getInventory().addItem(itemStack);
                } else if (itemStack.getType().equals(Material.IRON_ORE)) {
                    this.decoGame.getInventory().addItem(itemStack);
                } else if (itemStack.getType().equals(Material.COOKED_BEEF)) {
                    this.decoGame.getInventory().addItem(itemStack);
                }
            }
        }
        return null;
    }

    public static DecoGameManager getDecoGameEntity(Entity entiy) {
        for(DecoGameManager decoGameManager : Main.INSTANCE().uuidDecoGameManagerMap.values()) {
            if (decoGameManager.getDecoGame().getEntity() == entiy) {
                return decoGameManager;
            }
        }
        return null;
    }
}
