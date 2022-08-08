package fr.ylanouh.mineralcontest.commands;

import fr.ylanouh.mineralcontest.game.runnables.LobbyRunnable;
import fr.ylanouh.mineralcontest.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (s.equalsIgnoreCase("start")) {
            if (strings.length == 0) {
                if (Main.INSTANCE().getLobbyRunnable().isStarted()) {
                    return true;
                }
                LobbyRunnable lobbyRunnable = new LobbyRunnable(true);
                Main.INSTANCE().setLobbyRunnable(lobbyRunnable);
                lobbyRunnable.setStarted(true);
                lobbyRunnable.runTaskTimerAsynchronously(Main.INSTANCE(), 0L, 20L);

            }else if (strings[0].equalsIgnoreCase("force")) {
                if (Main.INSTANCE().getLobbyRunnable() != null) {
                    Main.INSTANCE().getLobbyRunnable().cancel();

                    LobbyRunnable lobbyRunnable = new LobbyRunnable(true);
                    Main.INSTANCE().setLobbyRunnable(lobbyRunnable);
                    lobbyRunnable.setStarted(true);
                    lobbyRunnable.runTaskTimerAsynchronously(Main.INSTANCE(), 0L, 20L);
                    lobbyRunnable.lobbyTimer = 5;
                } else {
                    LobbyRunnable lobbyRunnable = new LobbyRunnable(true);
                    Main.INSTANCE().setLobbyRunnable(lobbyRunnable);
                    lobbyRunnable.setStarted(true);
                    lobbyRunnable.runTaskTimerAsynchronously(Main.INSTANCE(), 0L, 20L);
                    lobbyRunnable.lobbyTimer = 5;
                }
            }else if (strings[0].equalsIgnoreCase("help")) {

            } else {
                commandSender.sendMessage("§4Error: §cCommand unconue");
            }
        }
        return false;
    }
}
