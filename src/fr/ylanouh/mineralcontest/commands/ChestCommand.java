package fr.ylanouh.mineralcontest.commands;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.animations.Chest;
import fr.ylanouh.mineralcontest.game.animations.Phase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (s.equalsIgnoreCase("chest")) {

            if (strings.length == 0) {
                return false;
            }

            if (strings[0].equalsIgnoreCase("arena")) {
                if (strings[1].equalsIgnoreCase("spawn")) {
                    if (Chest.isEventChestInArenaRun() == true) {
                        commandSender.sendMessage("§4Erreur: §cIl y a déjà un chest dans l'arène !");
                        return false;
                    }

                    Chest event = new Chest(Phase.PHASE_1);
                    event.addEvent();
                    Player player = (Player) commandSender;
                    return true;
                }
                if (strings[1].equalsIgnoreCase("remove")) {
                    if (Chest.isEventChestInArenaRun() == false) {
                        commandSender.sendMessage("§4Erreur: §cIl n'y a pas de chest dans l'arène !");
                        return false;
                    }

                    Chest event = Chest.giveEventChestInArenaRun();
                    event.remove();
                    commandSender.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replace(
                            "&", "§") +
                            " §2Le coffre a été surprimé.");
                    return true;
                }
            }else if (strings[0].equalsIgnoreCase("largage")) {

                if (strings[1].equalsIgnoreCase("spawn")) {
                    new Chest(Phase.LARGAGE).addEvent();
                    return true;
                }

                if (strings[1].equalsIgnoreCase("remove")) {
                    if (strings.length == 3) {
                        if (Chest.getEventLargageByID(Integer.parseInt(strings[2])) != null) {
                            Chest event = Chest.getEventLargageByID(Integer.parseInt(strings[2]));
                            event.remove();
                            commandSender.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replace(
                                    "&", "§") +
                                    " §2Vous avez bien surpprimé le largage avec l'id n°§c " + strings[2]);
                        }else {
                            commandSender.sendMessage("§4Erreur: §cIl n'y a pas de largage avec cette ID !");
                        }
                    }else {
                        commandSender.sendMessage("§4Erreur: §cCette argument n'est pas reconnu !");
                    }
                    return true;
                }
            }else {
                commandSender.sendMessage("§4Erreur: §cCette argument n'est pas reconnu !");
            }
        }
        return false;
    }
}
