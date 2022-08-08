package fr.ylanouh.mineralcontest.commands;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (s.equalsIgnoreCase("mctime")) {
            if (strings.length == 0 || strings.length > 1) {
                commandSender.sendMessage("§4Erreur: §cLa commande est /timer [nombre]");
                return false;
            }
            if (Utils.isNumeric(strings[0]) == false) {
                commandSender.sendMessage("§4Erreur: §cLa commande est /timer [nombre]");
                return false;
            }

            Main.INSTANCE().getGameRunnable().timer = Integer.parseInt(strings[0]);
            commandSender.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replaceAll(
                    "&", "§") +
                    " §9Vous avez mis le temp de la game à §c" + strings[0]);
        }
        return false;
    }
}
