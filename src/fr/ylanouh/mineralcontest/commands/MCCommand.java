package fr.ylanouh.mineralcontest.commands;

import fr.ylanouh.mineralcontest.Main;
import fr.ylanouh.mineralcontest.game.GameState;
import fr.ylanouh.mineralcontest.game.animations.Chest;
import fr.ylanouh.mineralcontest.game.animations.Phase;
import fr.ylanouh.mineralcontest.game.runnables.LobbyRunnable;
import fr.ylanouh.mineralcontest.game.teams.Teams;
import fr.ylanouh.mineralcontest.utils.Utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class MCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player)commandSender;
            String messageHelp = "§f§m---------------- §6§l> Mineral Contest §cBeta <§f§m----------------\n" +
                    "§e/mc help - help command\n" +
                    "/mc team spawn [TeamName]\n" +
                    "/mc team protection set [Pos1] [Pos2] [TeamName] - Définie les positions du cuboid\n" +
                    "/mc team protection remove [TeamName] - Supprime les protections des bases\n" +
                    "/mc team arena [TeamName] - Définie le spawn dans l'arène\n" +
                    "/mc arena [Pos1] [Pos2]\n" +
                    "/mc arena remove\n" +
                    "/mc largage spawn - Spawn le largage dans la map\n" +
                    "/mc largage remove [ID] - Supprime le largage par ID\n" +
                    "/mc points [TeamName] [Nombre] - Modifie les points d'une team\n" +
                    "/mc chest spawn - Spawn le coffre dans l'arène\n" +
                    "/mc chest remove - Supprime le coffre dans l'arène\n" +
                    "/mc lobby - Définie le spawn au démarrage de la partie\n" +
                    "/mc timer [Nombre] - Modifie le timer\n" +
                    "/start force - Démarrage du jeux en mode force et skip du timer\n" +
                    "/start - Démarrage du jeux en mode force\n" +
                    "§f§m---------------- §6§l> Aide commande < §f§m-----------------\n" +
                    "§eTeamName: Rouge, Verte, Jaune, Bleu\n" +
                    "Pos: Pos1: 25.0,58.5,15.5 | Pos2: 25.0,58.5,15.5\n" +
                    "§f§m------------------------------ §3§l§nBy Ylanouh Aliass Java++";
            if (s.equalsIgnoreCase("mc")) {
                if (strings.length == 0) {
                    player.sendMessage(messageHelp);
                    return false;
                }
                if (strings[0].equalsIgnoreCase("help")) {
                    player.sendMessage(messageHelp);
                } else if (strings[0].equalsIgnoreCase("team")) {
                    if (strings[1].equalsIgnoreCase("spawn")) {
                        if (strings[2].equalsIgnoreCase("Bleu")) {
                            World world = player.getWorld();

                            String key = "teams.blue.spawn";
                            Main.INSTANCE().getConfig().set(key + ".worldname", world);
                            Main.INSTANCE().getConfig().set(key + ".pos", Utils.parseLocToString(player.getLocation()));
                            Main.INSTANCE().getConfig().set(key + ".yaw", player.getLocation().getYaw());
                            Main.INSTANCE().getConfig().set(key + ".pitch", player.getLocation().getPitch());

                            player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() + " §2Vous avez changer la location du spawn de la team " + Teams.BLUE.getName());
                            return true;
                        }
                        if (strings[2].equalsIgnoreCase("Rouge")) {
                            World world = player.getWorld();

                            String key = "teams.red.spawn";
                            Main.INSTANCE().getConfig().set(key + ".worldname", world);
                            Main.INSTANCE().getConfig().set(key + ".pos", Utils.parseLocToString(player.getLocation()));
                            Main.INSTANCE().getConfig().set(key + ".yaw", player.getLocation().getYaw());
                            Main.INSTANCE().getConfig().set(key + ".pitch", player.getLocation().getPitch());

                            player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() + " §2Vous avez changer la location du spawn de la team " + Teams.RED.getName());
                            return true;
                        }
                        if (strings[2].equalsIgnoreCase("Jaune")) {
                            World world = player.getWorld();

                            String key = "teams.yellow.spawn";
                            Main.INSTANCE().getConfig().set(key + ".worldname", world);
                            Main.INSTANCE().getConfig().set(key + ".pos", Utils.parseLocToString(player.getLocation()));
                            Main.INSTANCE().getConfig().set(key + ".yaw", player.getLocation().getYaw());
                            Main.INSTANCE().getConfig().set(key + ".pitch", player.getLocation().getPitch());

                            player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() + " §2Vous avez changer la location du spawn de la team " + Teams.YELLOW.getName());
                            return true;
                        }
                        if (strings[2].equalsIgnoreCase("Verte")) {
                            World world = player.getWorld();

                            String key = "teams.green.spawn";
                            Main.INSTANCE().getConfig().set(key + ".worldname", world);
                            Main.INSTANCE().getConfig().set(key + ".pos", Utils.parseLocToString(player.getLocation()));
                            Main.INSTANCE().getConfig().set(key + ".yaw", player.getLocation().getYaw());
                            Main.INSTANCE().getConfig().set(key + ".pitch", player.getLocation().getPitch());

                            player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() + " §2Vous avez changer la location du spawn de la team " + Teams.DARK_GREEN.getName());
                            return true;
                        }
                        player.sendMessage("§4Erreur: §cCette argument n'est pas reconnue");
                    } else if (strings[1].equalsIgnoreCase("protection")) {
                        if (strings[2].equalsIgnoreCase("set")) {
                            if (strings[5].equalsIgnoreCase("Bleu")) {
                                World world = player.getWorld();

                                String key = "teams.blue.protection";
                                Main.INSTANCE().getConfig().set(key + ".worldname", world);
                                Main.INSTANCE().getConfig().set(key + ".pos1", strings[3]);
                                Main.INSTANCE().getConfig().set(key + ".pos2", strings[4]);

                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez ajouté la protection de la team " +
                                        Teams.BLUE.getName());
                                return true;
                            }
                            if (strings[5].equalsIgnoreCase("Rouge")) {
                                World world = player.getWorld();

                                String key = "teams.red.protection";
                                Main.INSTANCE().getConfig().set(key + ".worldname", world);
                                Main.INSTANCE().getConfig().set(key + ".pos1", strings[3]);
                                Main.INSTANCE().getConfig().set(key + ".pos2", strings[4]);

                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez ajouté la protection de la team " +
                                        Teams.RED.getName());
                                return true;
                            }
                            if (strings[5].equalsIgnoreCase("Jaune")) {
                                World world = player.getWorld();

                                String key = "teams.yellow.protection";
                                Main.INSTANCE().getConfig().set(key + ".worldname", world);
                                Main.INSTANCE().getConfig().set(key + ".pos1", strings[3]);
                                Main.INSTANCE().getConfig().set(key + ".pos2", strings[4]);

                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez ajouté la protection de la team " +
                                        Teams.YELLOW.getName());
                                return true;
                            }
                            if (strings[5].equalsIgnoreCase("Verte")) {
                                World world = player.getWorld();

                                String key = "teams.green.protection";
                                Main.INSTANCE().getConfig().set(key + ".worldname", world);
                                Main.INSTANCE().getConfig().set(key + ".pos1", strings[3]);
                                Main.INSTANCE().getConfig().set(key + ".pos2", strings[4]);

                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez ajouté la protection de la team " +
                                        Teams.DARK_GREEN.getName());
                            }
                        } else if (strings[2].equalsIgnoreCase("remove")) {
                            if (strings[5].equalsIgnoreCase("Bleu")) {
                                World world = player.getWorld();

                                String key = "teams.blue.protection";
                                Main.INSTANCE().getConfig().set(key + ".worldname", null);
                                Main.INSTANCE().getConfig().set(key + ".pos1", null);
                                Main.INSTANCE().getConfig().set(key + ".pos2", null);

                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez supprimé la protection de la team " +
                                        Teams.BLUE.getName());
                                return true;
                            }
                            if (strings[5].equalsIgnoreCase("Rouge")) {
                                World world = player.getWorld();

                                String key = "teams.red.protection";
                                Main.INSTANCE().getConfig().set(key + ".worldname", null);
                                Main.INSTANCE().getConfig().set(key + ".pos1", null);
                                Main.INSTANCE().getConfig().set(key + ".pos2", null);

                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez supprimé la protection de la team " +
                                        Teams.RED.getName());
                                return true;
                            }
                            if (strings[5].equalsIgnoreCase("Jaune")) {
                                World world = player.getWorld();

                                String key = "teams.yellow.protection";
                                Main.INSTANCE().getConfig().set(key + ".worldname", null);
                                Main.INSTANCE().getConfig().set(key + ".pos1", null);
                                Main.INSTANCE().getConfig().set(key + ".pos2", null);

                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez supprimé la protection de la team " +
                                        Teams.YELLOW.getName());
                                return true;
                            }
                            if (strings[5].equalsIgnoreCase("Verte")) {
                                World world = player.getWorld();

                                String key = "teams.green.protection";
                                Main.INSTANCE().getConfig().set(key + ".worldname", null);
                                Main.INSTANCE().getConfig().set(key + ".pos1", null);
                                Main.INSTANCE().getConfig().set(key + ".pos2", null);

                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez changer la location du spawn de la team " +
                                        Teams.DARK_GREEN.getName());
                            }
                        }
                    } else if (strings[1].equalsIgnoreCase("arena")) {
                        if (strings[0].equalsIgnoreCase("bleu")) {
                            Main.INSTANCE().getConfig().set("arena.posBlue", Utils.parseLocToString(player.getLocation()));
                            return true;
                        }
                        if (strings[0].equalsIgnoreCase("jaune")) {
                            Main.INSTANCE().getConfig().set("arena.posYellow", Utils.parseLocToString(player.getLocation()));
                            return true;
                        }
                        if (strings[0].equalsIgnoreCase("verte")) {
                            Main.INSTANCE().getConfig().set("arena.posGreen", Utils.parseLocToString(player.getLocation()));
                            return true;
                        }
                        if (strings[0].equalsIgnoreCase("rouge")) {
                            Main.INSTANCE().getConfig().set("arena.posRed", Utils.parseLocToString(player.getLocation()));
                            return true;
                        }

                    }
                } else if (strings[0].equalsIgnoreCase("arena")) {
                    if (strings[1].equalsIgnoreCase("set")) {
                        Main.INSTANCE().getConfig().set("arenaProtect.pos1", strings[2]);
                        Main.INSTANCE().getConfig().set("arenaProtect.pos1", strings[3]);

                        player.sendMessage("§4Erreur: §cVous avez ajouté la protection de l'arène");
                    } else if (strings[1].equalsIgnoreCase("remove")) {
                        Main.INSTANCE().getConfig().set("arenaProtect.pos1", null);
                        Main.INSTANCE().getConfig().set("arenaProtect.pos1", null);

                        player.sendMessage("§4Erreur: §cVous avez supprimé la protection de l'arène");
                    }
                } else if (strings[0].equalsIgnoreCase("chest")) {
                    if (strings[1].equalsIgnoreCase("spawn")) {
                        if (Chest.isEventChestInArenaRun()) {
                            player.sendMessage("§4Erreur: §cIl y a déjà un chest dans l'arène !");
                            return false;
                        }

                        Chest event = new Chest(Phase.PHASE_1);
                        event.addEvent();
                    } else if (strings[1].equalsIgnoreCase("remove")) {
                        if (!Chest.isEventChestInArenaRun()) {
                            player.sendMessage("§4Erreur: §cIl n'y a pas de chest dans l'arène !");
                            return false;
                        }

                        Chest event = Chest.giveEventChestInArenaRun();
                        event.remove();
                        player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                " §2Le coffre a été surprimé.");
                    }
                }else if (strings[0].equalsIgnoreCase("largage")) {

                    if (strings[1].equalsIgnoreCase("spawn")) {
                        new Chest(Phase.LARGAGE).addEvent();
                        return false;
                    }

                    if (strings[1].equalsIgnoreCase("remove")) {
                        if (strings.length == 3) {
                            if (Chest.getEventLargageByID(Integer.parseInt(strings[2])) != null) {
                                Chest event = Chest.getEventLargageByID(Integer.parseInt(strings[2]));
                                event.remove();
                                player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                                        " §2Vous avez bien supprimé le largage avec l'id n°§c " + strings[2]);
                            }else {
                                player.sendMessage("§4Erreur: §cIl n'y a pas de largage avec cette ID !");
                            }
                        }else {
                            player.sendMessage("§4Erreur: §cCette argument n'est pas reconnue !");
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("points")) {
                    if (!Utils.isNumeric(strings[2])) {
                        player.sendMessage("§4Erreur: §cL'argument " + strings[1] + " doit etre un nombre");
                        return true;
                    }

                    if (!GameState.isState(GameState.GAME)) {
                        player.sendMessage("§4Erreur: §cLa partie n'a pas commencée");
                        return true;
                    }

                    if (strings[1].equalsIgnoreCase("Bleu")) {
                        Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.BLUE, Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("Jaune")) {
                        Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.YELLOW, Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("Rouge")) {
                        Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.RED, Integer.parseInt(strings[2]));
                    } else if (strings[1].equalsIgnoreCase("Verte")) {
                        Main.INSTANCE().getTeamsManager().setTeamPoints(Teams.DARK_GREEN, Integer.parseInt(strings[2]));
                    }

                    player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() + " §2La team " + strings[1] +
                            " §2est maintenant à §c" + strings[2] + " point(s)");
                } else if (strings[0].equalsIgnoreCase("start")) {
                    if (strings.length == 2) {
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
                    }else {
                        if (Main.INSTANCE().getLobbyRunnable().isStarted()) {
                            return true;
                        }
                        LobbyRunnable lobbyRunnable = new LobbyRunnable(true);
                        Main.INSTANCE().setLobbyRunnable(lobbyRunnable);
                        lobbyRunnable.setStarted(true);
                        lobbyRunnable.runTaskTimerAsynchronously(Main.INSTANCE(), 0L, 20L);
                    }
                } else if (strings[0].equalsIgnoreCase("timer")) {

                    if (!Utils.isNumeric(strings[1])) {
                        player.sendMessage("§4Erreur: §cL'argument " + strings[1] + " doit etre un nombre");
                        return false;
                    }

                    Main.INSTANCE().getGameRunnable().timer = Integer.parseInt(strings[1]);
                    player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() +
                            " §9Vous avez mis le temps de la game à §c" + strings[1]);
                } else if (strings[0].equalsIgnoreCase("lobby")) {
                    World world = player.getWorld();

                    String key = "lobbySpawn";
                    Main.INSTANCE().getConfig().set(key + ".worldname", world);
                    Main.INSTANCE().getConfig().set(key + ".x", player.getLocation().getX());
                    Main.INSTANCE().getConfig().set(key + ".y", player.getLocation().getX());
                    Main.INSTANCE().getConfig().set(key + ".z", player.getLocation().getX());
                    Main.INSTANCE().getConfig().set(key + ".yaw", player.getLocation().getYaw());
                    Main.INSTANCE().getConfig().set(key + ".pitch", player.getLocation().getPitch());

                    player.sendMessage(Main.INSTANCE().getGameSettings().getGametype() + " §2Vous avez changer la location du spawn");
                } else {
                    player.sendMessage("§4Erreur: §cCette argument n'est pas reconnue !");
                }
            }
        }
        return false;
    }
}