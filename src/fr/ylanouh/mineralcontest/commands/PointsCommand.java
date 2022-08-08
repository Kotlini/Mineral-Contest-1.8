package fr.ylanouh.mineralcontest.commands;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PointsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length == 0 || strings.length < 2) {
            commandSender.sendMessage("§4Erreur: §cLa commmande est /mcpoints [TeamName] [Point(s)]");
            return true;
        }
        if (!Utils.isNumeric(strings[1])) {
            commandSender.sendMessage("§4Erreur: §cLa commmande est /mcpoints [TeamName] [Point(s)]");
            return true;
        }
        if (!GameState.isState(GameState.GAME)) {
            commandSender.sendMessage("§4Erreur: §cLa partie n'a pas commencé ou est terminé ");
            return true;
        }

        if (strings[0].equalsIgnoreCase("Bleu")) {
            Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.BLUE, Integer.parseInt(strings[1]));
        } else if (strings[0].equalsIgnoreCase("Jaune")) {
            Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.YELLOW, Integer.parseInt(strings[1]));
        } else if (strings[0].equalsIgnoreCase("Rouge")) {
            Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.RED, Integer.parseInt(strings[1]));
        } else if (strings[0].equalsIgnoreCase("Verte")) {
            Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.DARK_GREEN, Integer.parseInt(strings[1]));
        }
        commandSender.sendMessage("§2La team " + strings[0] + " §2est maintenant à §c" + strings[1] + " point(s)");

        return false;
    }
}
