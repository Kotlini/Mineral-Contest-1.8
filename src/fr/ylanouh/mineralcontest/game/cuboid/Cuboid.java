package fr.ylanouh.mineralcontest.game.cuboid;

import fr.ylanouh.mineralcontest.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Cuboid {
    public Location minLoc;

    public Location maxLoc;

    public Cuboid(Location firstPoint, Location secondPoint) {
        this.minLoc = new Location(firstPoint.getWorld(), min(firstPoint.getX(), secondPoint.getX()), min(firstPoint.getY(), secondPoint.getY()), min(firstPoint.getZ(), secondPoint.getZ()));
        this.maxLoc = new Location(firstPoint.getWorld(), max(firstPoint.getX(), secondPoint.getX()), max(firstPoint.getY(), secondPoint.getY()), max(firstPoint.getZ(), secondPoint.getZ()));
    }

    public double min(double a, double b) {
        return Math.min(a, b);
    }

    public double max(double a, double b) {
        return Math.max(a, b);
    }

    public boolean isInArea(Location loc) {
        return (this.minLoc.getX() <= loc.getX() && this.minLoc.getZ() <= loc.getZ() && this.maxLoc.getX() >= loc.getX() && this.maxLoc.getZ() >= loc.getZ());
    }

    public Location getMiddle() {
        double a = (this.maxLoc.getX() - this.minLoc.getX()) / 2.0D + this.minLoc.getX();
        double b = (this.maxLoc.getZ() - this.minLoc.getZ()) / 2.0D + this.minLoc.getZ();
        return new Location(Bukkit.getWorld(Main.INSTANCE().getConfig().getString("lobbySpawn.worldname")), a, this.minLoc.getY(), b);
    }
}
