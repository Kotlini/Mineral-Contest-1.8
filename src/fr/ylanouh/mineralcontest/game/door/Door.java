package fr.ylanouh.mineralcontest.game.door;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Door {

    private Location locCenterDoor;

    private Teams team;

    public Door(Teams team, Location locCenterDoor) {
        this.locCenterDoor = locCenterDoor;
        this.team = team;
    }

    public void setTeam(Teams team) {
        this.team = team;
    }

    public Teams getTeam() {
        return team;
    }

    public Location getLocCenterDoor() {
        return locCenterDoor;
    }

    public void setLocCenterDoor(Location locCenterDoor) {
        this.locCenterDoor = locCenterDoor;
    }

    public void add() {
        Main.INSTANCE().teamDoorMap.put(this.team, this);
        createDoor();
    }

    public void getPlayerInDoor(Player player) {
        Location loc = player.getLocation();

        if (this.locCenterDoor.distance(loc) <= 2) {
            removeDoor();
        }
    }

    public void getPlayerOutDoor(Player player) {
        Location loc = player.getLocation();

        if (this.locCenterDoor.distance(loc) >= 2) {
            createDoor();
        }
    }

    public static Door getDoor(Player player) {
        Teams team = Main.INSTANCE().getTeamsManager().getPlayerTeam(player);

        for (Door door : Main.INSTANCE().teamDoorMap.values()) {
            if (door.team == team) {
                return door;
            }
        }
        return null;
    }

    public void createDoor() {
        Location location = this.locCenterDoor;

        if (this.team == Teams.RED) {
            Utils.add(location, 0, -1, 0).getBlock().setTypeIdAndData(95, (byte) 14, true);
            Utils.add(location, 0, -1, 1).getBlock().setTypeIdAndData(159, (byte) 14, true);
            Utils.add(location, 0, -1, -1).getBlock().setTypeIdAndData(159, (byte) 14, true);

            Utils.add(location, 0, 0, -1).getBlock().setTypeIdAndData(95, (byte) 14, true);
            Utils.add(location, 0, 0, 0).getBlock().setTypeIdAndData(159, (byte) 14, true);
            Utils.add(location, 0, 0, 1).getBlock().setTypeIdAndData(95, (byte) 14, true);

            Utils.add(location, 0, 1, 1).getBlock().setTypeIdAndData(159, (byte) 14, true);
            Utils.add(location, 0, 1, 0).getBlock().setTypeIdAndData(95, (byte) 14, true);
            Utils.add(location, 0, 1, -1).getBlock().setTypeIdAndData(159, (byte) 14, true);

        } else if (this.team == Teams.BLUE) {

            Utils.add(location, 0, -1, 0).getBlock().setTypeIdAndData(95, (byte) 11, true);
            Utils.add(location, -1, -1, 0).getBlock().setTypeIdAndData(159, (byte) 11, true);
            Utils.add(location, 1, -1, 0).getBlock().setTypeIdAndData(159, (byte) 11, true);

            Utils.add(location, -1, 0, 0).getBlock().setTypeIdAndData(95, (byte) 11, true);
            Utils.add(location, 0, 0, 0).getBlock().setTypeIdAndData(159, (byte) 11, true);
            Utils.add(location, 1, 0, 0).getBlock().setTypeIdAndData(95, (byte) 11, true);

            Utils.add(location, -1, 1, 0).getBlock().setTypeIdAndData(159, (byte) 11, true);
            Utils.add(location, 0, 1, 0).getBlock().setTypeIdAndData(95, (byte) 11, true);
            Utils.add(location, 1, 1, 0).getBlock().setTypeIdAndData(159, (byte) 11, true);

        } else if (this.team == Teams.DARK_GREEN) {
            Utils.add(location, 0, -1, 0).getBlock().setTypeIdAndData(95, (byte) 13, true);
            Utils.add(location, -1, -1, 0).getBlock().setTypeIdAndData(159, (byte) 13, true);
            Utils.add(location, 1, -1, 0).getBlock().setTypeIdAndData(159, (byte) 13, true);

            Utils.add(location, -1, 0, 0).getBlock().setTypeIdAndData(95, (byte) 13, true);
            Utils.add(location, 0, 0, 0).getBlock().setTypeIdAndData(159, (byte) 13, true);
            Utils.add(location, 1, 0, 0).getBlock().setTypeIdAndData(95, (byte) 13, true);

            Utils.add(location, -1, 1, 0).getBlock().setTypeIdAndData(159, (byte) 13, true);
            Utils.add(location, 0, 1, 0).getBlock().setTypeIdAndData(95, (byte) 13, true);
            Utils.add(location, 1, 1, 0).getBlock().setTypeIdAndData(159, (byte) 13, true);

        } else if (this.team == Teams.YELLOW) {

            Utils.add(location, 0, -1, 0).getBlock().setTypeIdAndData(95, (byte) 4, true);
            Utils.add(location, 0, -1, 1).getBlock().setTypeIdAndData(159, (byte) 4, true);
            Utils.add(location, 0, -1, -1).getBlock().setTypeIdAndData(159, (byte) 4, true);

            Utils.add(location, 0, 0, -1).getBlock().setTypeIdAndData(95, (byte) 4, true);
            Utils.add(location, 0, 0, 0).getBlock().setTypeIdAndData(159, (byte) 4, true);
            Utils.add(location, 0, 0, 1).getBlock().setTypeIdAndData(95, (byte) 4, true);

            Utils.add(location, 0, 1, 1).getBlock().setTypeIdAndData(159, (byte) 4, true);
            Utils.add(location, 0, 1, 0).getBlock().setTypeIdAndData(95, (byte) 4, true);
            Utils.add(location, 0, 1, -1).getBlock().setTypeIdAndData(159, (byte) 4, true);

        }
    }

    public void removeDoor() {
        Location location = this.locCenterDoor;

        if (this.team == Teams.RED) {
            Utils.add(location, 0, -1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 0, -1, 1).getBlock().setType(Material.AIR);
            Utils.add(location, 0, -1, -1).getBlock().setType(Material.AIR);

            Utils.add(location, 0, 0, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 0, 0, 1).getBlock().setType(Material.AIR);
            Utils.add(location, 0, 0, -1).getBlock().setType(Material.AIR);

            Utils.add(location, 0, 1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 0, 1, 1).getBlock().setType(Material.AIR);
            Utils.add(location, 0, 1, -1).getBlock().setType(Material.AIR);

        } else if (this.team == Teams.BLUE) {

            Utils.add(location, 0, -1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, -1, -1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 1, -1, 0).getBlock().setType(Material.AIR);

            Utils.add(location, 0, 0, 0).getBlock().setType(Material.AIR);
            Utils.add(location, -1, 0, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 1, 0, 0).getBlock().setType(Material.AIR);

            Utils.add(location, 0, 1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, -1, 1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 1, 1, 0).getBlock().setType(Material.AIR);

        } else if (this.team == Teams.DARK_GREEN) {
            Utils.add(location, 0, -1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, -1, -1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 1, -1, 0).getBlock().setType(Material.AIR);

            Utils.add(location, 0, 0, 0).getBlock().setType(Material.AIR);
            Utils.add(location, -1, 0, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 1, 0, 0).getBlock().setType(Material.AIR);

            Utils.add(location, 0, 1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, -1, 1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 1, 1, 0).getBlock().setType(Material.AIR);

        } else if (this.team == Teams.YELLOW) {

            Utils.add(location, 0, -1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 0, -1, 1).getBlock().setType(Material.AIR);
            Utils.add(location, 0, -1, -1).getBlock().setType(Material.AIR);

            Utils.add(location, 0, 0, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 0, 0, 1).getBlock().setType(Material.AIR);
            Utils.add(location, 0, 0, -1).getBlock().setType(Material.AIR);

            Utils.add(location, 0, 1, 0).getBlock().setType(Material.AIR);
            Utils.add(location, 0, 1, 1).getBlock().setType(Material.AIR);
            Utils.add(location, 0, 1, -1).getBlock().setType(Material.AIR);

        }
    }
}
