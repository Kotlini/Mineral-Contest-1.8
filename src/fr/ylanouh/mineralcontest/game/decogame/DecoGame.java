package fr.ylanouh.mineralcontest.game.decogame;

import fr.ylanouh.mineralcontest.Main;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

public class DecoGame {


    private Location locPlayer;

    private Player player;

    private int timer;

    private boolean isReco;

    private Entity entity;

    private Inventory inventory;

    private BukkitTask task;

    private boolean entityIsDead;
    public DecoGame(Location location, Player player, BukkitTask task, Inventory inventory, Entity entity) {
        this.locPlayer = location;
        this.player = player;
        this.timer = Main.INSTANCE().getConfig().getInt("decoGameTime");
        this.task = task;
        this.inventory = inventory;
        this.entity = entity;
        this.entityIsDead = false;
        this.isReco = false;
    }

    public int getTimer() {
        return timer;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocPlayer() {
        return locPlayer;
    }

    public BukkitTask getTask() {
        return task;
    }

    public boolean isReco() {
        return isReco;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public boolean isEntityIsDead() {
        return entityIsDead;
    }

    public void setEntityIsDead(boolean entityIsDead) {
        this.entityIsDead = entityIsDead;
    }

    public void setReco(boolean reco) {
        isReco = reco;
    }
}