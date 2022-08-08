package fr.ylanouh.mineralcontest.commands;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.game.teams.TeamsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Teams team = Main.INSTANCE().getTeamsManager().getPlayerTeam(player);
            if (s.equalsIgnoreCase("arene")) {
                for (Player playersTeam : Main.INSTANCE().getTeamsManager().getTeamPlayerList(team)) {
                    playersTeam.teleport(TeamsManager.getLocArenaToTeam(team));
                }
            }
        }
        return false;
    }
}
