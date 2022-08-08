package fr.ylanouh.mineralcontest.commands;

import fr.ylanouh.mineralcontest.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player)commandSender;
            String messageHelp = "§f§m---------------- §6§l> Mineral Contest §cV1 <§f§m----------------\n" +
                    "/mc help - help command\n" +
                    "/mc team spawn set\n" +
                    "/mc team protection set [Pos1] [Pos2] [TeamName] - Définie la protection de la base\n" +
                    "/mc team protection remove [TeamName] - Supprime les protections des bases\n" +
                    "/mc team arena set [TeamName] - Définie le spawn dans l'arène\n" +
                    "/mc chest set\n - Définie le coffre dans l'arène\n" +
                    "/mc lobbyspawn - Set le spawn au démarrage de la partie\n" +
                    "/start force - Démarrage du jeux en mode force et skip du timer\n" +
                    "/start - Démarrage du jeux en mode force\n" +
                    "§f§m--------------------- > Aide commande < -------------------\n" +
                    "TeamName: Rouge, Verte, Jaune, Bleu\n" +
                    "Pos: Pos1: 25.0,58.5,15.5 | Pos2: 25.0,58.5,15.5\n" +
                    "§f§m------------------------------ §3§l§nBy Ylanouh Aliass Java++\n";





            if (s.equalsIgnoreCase("mc")) {
                if (strings.length == 0) {
                    player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() + "");
                }
            }
        }

        return false;
    }
}
