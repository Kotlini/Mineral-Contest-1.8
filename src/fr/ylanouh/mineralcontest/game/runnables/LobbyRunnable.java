package fr.ylanouh.mineralcontest.game.runnables;

import fr.ylanouh.mineralcontest.game.GamePlayer;
import fr.ylanouh.mineralcontest.game.GameSettings;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.scoreboard.FastBoard;
import fr.ylanouh.mineralcontest.game.scoreboard.ScoreboardDispatch;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class LobbyRunnable extends BukkitRunnable {
    private final GameSettings gameSettings = Main.INSTANCE().getGameSettings();

    public int lobbyTimer = this.gameSettings.getCountdownStartTimer();

    private boolean started = false;

    private final boolean forceStart;

    private static boolean finish = false;

    public LobbyRunnable(boolean forceStart) {
        this.forceStart = forceStart;
    }

    public void run() {
        if (this.started) {
            if (Bukkit.getOnlinePlayers().size() < this.gameSettings.getMinPlayers() && !this.forceStart) {
                Bukkit.broadcastMessage(Main.INSTANCE().getGameSettings().getGametype().replace("&", "§") + " §eIl n'y a pas assez de joueurs pour démarer la partie !");
                this.lobbyTimer = this.gameSettings.getCountdownStartTimer();
                this.started = false;
                this.cancel();
            }
            if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers() && this.lobbyTimer >= 20) {
                this.lobbyTimer = 15;
            }

            if (this.lobbyTimer == 0) {
                GameState.setState(GameState.WAITTING);
                String message = "§f-------------------------------------------------\n" +
                        "§e[§4ATTENTION§e] §cTant que tous les joueurs n'auront pas " +
                        "choisis leurs classe et équipe la partie ne démarera pas.\n" +
                        "§f-------------------------------------------------\n";
                Utils.sendMessageAllPlayers(message);
                setFinish(true);

                VerifPlayerGame start = new VerifPlayerGame();
                start.runTaskTimer(Main.INSTANCE(), 0, 20);
                this.cancel();
            }

            if (this.lobbyTimer == 120 || this.lobbyTimer == 90 || this.lobbyTimer == 60 || this.lobbyTimer == 30 ||
                    this.lobbyTimer == 15 || this.lobbyTimer == 10 || this.lobbyTimer <= 5) {
                Bukkit.broadcastMessage(Main.INSTANCE().getGameSettings().getGametype().
                        replace("&", "§")
                        + " §ede la partie dans §c"+ Utils.changeFormatInt("mm:ss", this.lobbyTimer));

                Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(),
                        Sound.NOTE_PLING, 2.0F, 1.0F));
            }

            Bukkit.getOnlinePlayers().forEach(playerOnline -> playerOnline.setLevel(this.lobbyTimer));

            for (FastBoard board : Main.INSTANCE().getBoard().values()) {
                ScoreboardDispatch.updateBoardStarting(board);
            }

            this.lobbyTimer--;
        }
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStarted() {
        return this.started;
    }

    public static void setFinish(boolean finish) {
        LobbyRunnable.finish = finish;
    }

    public static boolean isFinish() {
        return finish;
    }
}
