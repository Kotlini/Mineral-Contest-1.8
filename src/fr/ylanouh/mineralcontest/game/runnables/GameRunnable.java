package fr.ylanouh.mineralcontest.game.runnables;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.Map.WorldManager;
import fr.ylanouh.mineralcontest.game.animations.Chest;
import fr.ylanouh.mineralcontest.game.animations.Phase;
import fr.ylanouh.mineralcontest.game.classement.ClassementManager;
import fr.ylanouh.mineralcontest.game.scoreboard.FastBoard;
import fr.ylanouh.mineralcontest.game.scoreboard.ScoreboardDispatch;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {

    public int timer = 3600;
    public boolean start = false;

    @Override
    public void run() {
        if (start) {

            if (timer == 0 || !GameState.isState(GameState.GAME)) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    Utils.resetPlayersKit(player);
                    player.sendMessage(Main.INSTANCE().getGameSettings().getGametype().replaceAll(
                            "&", "§") +
                            "§2§lReset de la partie automatique dans §c30 secondes");
                });

                ClassementManager classementManager = new ClassementManager(true);
                Main.INSTANCE().setClassementManager(classementManager);

                this.cancel();
            }

            //Event Chest
            if (timer == 2700) {
                Chest event = new Chest(Phase.PHASE_1);
                event.addEvent();
            } else if (timer == 1500) {
                Chest event = new Chest(Phase.PHASE_2);
                event.addEvent();
            } else if (timer == 600) {
                Chest event = new Chest(Phase.PHASE_3);
                event.addEvent();
            } else if (timer == 360) {
                Chest event = new Chest(Phase.PHASE_4);
                event.addEvent();
            }

            //Event Largage
            if (timer == 3600) {
                Chest event = new Chest(Phase.LARGAGE);
                event.addEvent();
            } else if (timer == 3000) {
                Chest event = new Chest(Phase.LARGAGE);
                event.addEvent();
            } else if (timer == 2400)  {
                Chest event = new Chest(Phase.LARGAGE);
                event.addEvent();
            } else if (timer == 1800) {
                Chest event = new Chest(Phase.LARGAGE);
                event.addEvent();
            } else if (timer == 1200) {
                Chest event = new Chest(Phase.LARGAGE);
                event.addEvent();
            } else if (timer == 600) {
                Chest event = new Chest(Phase.LARGAGE);
                event.addEvent();
            } else if (timer == 400) {
                Chest event = new Chest(Phase.LARGAGE);
                event.addEvent();
            }

            for (FastBoard board : Main.INSTANCE().getBoard().values()) {
                ScoreboardDispatch.updateBoardGame(board);
            }

            timer--;
        }
    }
}
