package fr.ylanouh.mineralcontest.game.scoreboard;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.GamePlayer;
import fr.ylanouh.mineralcontest.game.kit.Kit;
import fr.ylanouh.mineralcontest.utils.ArrowTargetUtils;
import fr.ylanouh.mineralcontest.utils.Utils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

public class ScoreboardDispatch {

    public static void updateBoardWatting(FastBoard board) {
        EntityPlayer msPlayer = ((CraftPlayer) board.getPlayer()).getHandle();
        int nbJ = Main.INSTANCE().getGameSettings().getMaxPlayers()-Bukkit.getOnlinePlayers().size();
        board.updateLines(
                "     §8§l› §7" + Utils.getDate(),
                "§7",
                "§8§l› §7En attente de §c" + nbJ,
                "§8",
                "§8§l› §7Ping: §c" + msPlayer.ping,
                "§7",
                "§9Communauté §3§lRKM"
        );
    }

    public static void updateBoardStarting(FastBoard board) {
        EntityPlayer msPlayer = ((CraftPlayer) board.getPlayer()).getHandle();
        int timer = Main.INSTANCE().getLobbyRunnable().lobbyTimer;
        board.updateLines(
                "     §8§l› §7" + Utils.getDate(),
                "§7",
                "§8§l› §7Démarrage §c" + Utils.changeFormatInt("mm:ss", timer),
                "§8§l› §7Joueurs: §c" + Bukkit.getOnlinePlayers().size() + "§7/§8" +
                        Main.INSTANCE().getGameSettings().getMaxPlayers(),
                "§6",
                "§8§l› §7Ping: §c" + msPlayer.ping,
                "§7",
                "§9Communauté §3§lRKM"
        );
    }

    public static void updateBoardGame(FastBoard board) {
        EntityPlayer msPlayer = ((CraftPlayer) board.getPlayer()).getHandle();
        board.updateLines(
                "§4",
                "§8§l›› §7Timer: §c" + Utils.changeFormatInt("mm:ss",
                        Main.INSTANCE().getGameRunnable().timer),
                "§7",
                "§8§l›› §f" + board.getPlayer().getName(),
                "§8› §7Kit: §c" + Kit.getKit(board.getPlayer()),
                "§8› §7Equipe: " + Main.INSTANCE().getTeamsManager().getPlayerTeam(board.getPlayer()).getName(),
                "§8› §7Ping: §c" + msPlayer.ping,
                "§8› §7Kill: §c" + GamePlayer.getGamePlayer(board.getPlayer().getUniqueId()).getKills(),
                "§7",
                "§8§l›› " + Main.INSTANCE().getTeamsManager().getPlayerTeam(board.getPlayer()).getName() +
                        " " + ArrowTargetUtils.calculateArrow(board.getPlayer(),
                        Main.INSTANCE().getTeamsManager().getTeamLocation(
                                Main.INSTANCE().getTeamsManager().getPlayerTeam(board.getPlayer())
                        )),
                "§8› §7Points: §c" + Main.INSTANCE().getTeamsManager().getTeamPoints(Main.INSTANCE().getTeamsManager().
                        getPlayerTeam(board.getPlayer())),
                "§8› §7Boost: §c" + Main.INSTANCE().getTeamsManager().getTeamBoost(Main.INSTANCE().getTeamsManager().
                        getPlayerTeam(board.getPlayer())),
                "§5",
                "§9Communauté §3§lRKM"
        );
    }
}