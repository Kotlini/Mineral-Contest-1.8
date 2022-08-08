package fr.ylanouh.mineralcontest.game.cuboid;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import org.bukkit.Location;

public class CuboidManager {

    private Teams team;
    private Cuboid cuboid;

    public CuboidManager(Teams team, Cuboid cuboid) {
        this.team = team;
        this.cuboid = cuboid;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public Teams getTeam() {
        return team;
    }

    public void addCuboid() {
        Main.INSTANCE().teamCuboidMap.put(this.team, this);
    }

    public void setTeam(Teams team) {
        this.team = team;
    }

    public static CuboidManager getTeamToLoc(Location location) {
        for (CuboidManager cuboidManager : Main.INSTANCE().teamCuboidMap.values()) {
            if (cuboidManager.getCuboid().isInArea(location)) {
                return cuboidManager;
            }
        }
        return null;
    }
}
