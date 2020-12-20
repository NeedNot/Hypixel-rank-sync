package net.neednot.listeners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.hypixel.api.reply.GuildReply;
import net.neednot.RankSync;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import net.hypixel.api.HypixelAPI;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class Sync implements Listener {

    RankSync plugin;

    public Sync(RankSync pl) {
        plugin = pl;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){

            event.setJoinMessage(event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).getPrefix() + event.getPlayer().getName() + " §eJoined the game.");

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        if (board.getTeam(event.getPlayer().getName()) == null) {
            Team team = board.registerNewTeam(event.getPlayer().getName());
            team.addEntry(event.getPlayer().getName());
        }
        else {
            board.getTeam(event.getPlayer().getName()).unregister();
            Team team = board.registerNewTeam(event.getPlayer().getName());
            team.addEntry(event.getPlayer().getName());
        }


        UUID apiKey = UUID.fromString(plugin.getConfig().getString("API key"));
        HypixelAPI api = new HypixelAPI(apiKey);

        api.getPlayerByUuid(event.getPlayer().getUniqueId()).whenComplete((response, error) -> {

            //Check if there was an API error
            if (error != null) {
                error.printStackTrace();
                return;
           }

            if (plugin.getConfig().getBoolean("Show guild rank")) {
                api.getGuildByPlayer(event.getPlayer().getUniqueId()).whenComplete((gresponse, gerror) -> {

                    // Check if there was an API error
                    if (gerror != null) {
                        error.printStackTrace();
                        return;
                    }

                    GuildReply.Guild guild = gresponse.getGuild();


                    if (guild != null) {

                        for (int i = 0; i < 125; i++) {
                            if (guild.getMembers().get(i).getUuid().equals(event.getPlayer().getUniqueId())) {
                                event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setSuffix("§7 [" + guild.getMembers().get(i).getRank() + "]");
                                break;
                            }
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "Sorry but you seem to have no guild home.");
                    }
                });
            }

            JsonObject player = response.getPlayer();

            //if (RankSync.config.getBoolean("Show guild ranks")) {
            //    event.getPlayer().getScoreboard().getTeam(event.getPlayer()).setPrefix("");
            //}

            if (getFieldOrNA("rank", player) == null) {

                if (getFieldOrNA("monthlyPackageRank", player) != null) {

                    if (getFieldOrNA("monthlyPackageRank", player).toString().equalsIgnoreCase("SUPERSTAR")) {

                        String plus = getFieldOrNA("rankPlusColor", player).toString();

                        String plus_color = "";

                        switch (plus) {

                            case "RED":
                                plus_color = "§c++";
                                break;
                            case "GOLD":
                                plus_color = "§6++";
                                break;
                            case "GREEN":
                                plus_color = "§a++";
                                break;
                            case "YELLOW":
                                plus_color = "§e++";
                                break;
                            case "LIGHT_PURPLE":
                                plus_color = "§d++";
                                break;
                            case "WHITE":
                                plus_color = "§f++";
                                break;
                            case "BLUE":
                                plus_color = "§9++";
                                break;
                            case "DARK_GREEN":
                                plus_color = "§2++";
                                break;
                            case "DARK_RED":
                                plus_color = "§4++";
                                break;
                            case "DARK_AQUA":
                                plus_color = "§3++";
                                break;
                            case "DARK_PINK":
                                plus_color = "§5++";
                                break;
                            case "DARK_GRAY":
                                plus_color = "§8++";
                                break;
                            case "BLACK":
                                plus_color = "§0++";
                                break;
                            case "DARK_BLUE":
                                plus_color = "§1++";
                                break;
                        }


                        if (getFieldOrNA("monthlyRankColor", player).equalsIgnoreCase("AQUA")) {
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.AQUA);
                            String prefix = "§b[MVP" + plus_color + "§b] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");
                        }
                        else {
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.GOLD);
                            String prefix = "§6[MVP" + plus_color + "§6] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");
                        }
                    }

                }
                if (getFieldOrNA("PackageRank", player) == null) {
                    if (getFieldOrNA("newPackageRank", player) != null) {

                        if (getFieldOrNA("newPackageRank", player).toString().equalsIgnoreCase("MVP_PLUS")) {

                            String plus = getFieldOrNA("rankPlusColor", player).toString();

                            String plus_color = "";

                            switch (plus) {

                                case "RED":
                                    plus_color = "§c+";
                                    break;
                                case "GOLD":
                                    plus_color = "§6+";
                                    break;
                                case "GREEN":
                                    plus_color = "§a+";
                                    break;
                                case "YELLOW":
                                    plus_color = "§e+";
                                    break;
                                case "LIGHT_PURPLE":
                                    plus_color = "§d+";
                                    break;
                                case "WHITE":
                                    plus_color = "§f+";
                                    break;
                                case "BLUE":
                                    plus_color = "§9+";
                                    break;
                                case "DARK_GREEN":
                                    plus_color = "§2+";
                                    break;
                                case "DARK_RED":
                                    plus_color = "§4+";
                                    break;
                                case "DARK_AQUA":
                                    plus_color = "§3+";
                                    break;
                                case "DARK_PINK":
                                    plus_color = "§5+";
                                    break;
                                case "DARK_GRAY":
                                    plus_color = "§8+";
                                    break;
                                case "BLACK":
                                    plus_color = "§0+";
                                    break;
                                case "DARK_BLUE":
                                    plus_color = "§1+";
                                    break;
                            }

                            String prefix = "§b[MVP" + plus_color + "§b] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.AQUA);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");
                        }
                        if (getFieldOrNA("newPackageRank", player).toString().equalsIgnoreCase("MVP")) {

                            String prefix = "§b[MVP] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.AQUA);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");

                        }
                        if (getFieldOrNA("newPackageRank", player).toString().equalsIgnoreCase("VIP_PLUS")) {

                            String prefix = "§a[VIP§e+§a] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.GREEN);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");

                        }
                        if (getFieldOrNA("newPackageRank", player).toString().equalsIgnoreCase("VIP")) {

                            String prefix = "§a[VIP] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.GREEN);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");

                        }
                    }
                }
                if (getFieldOrNA("newPackageRank", player) == null) {
                    if (getFieldOrNA("PackageRank", player) != null) {

                        if (getFieldOrNA("PackageRank", player).toString().equalsIgnoreCase("MVP_PLUS")) {

                            String plus = getFieldOrNA("rankPlusColor", player).toString();

                            String plus_color = "";

                            switch (plus) {

                                case "RED":
                                    plus_color = "§c+";
                                    break;
                                case "GOLD":
                                    plus_color = "§6+";
                                    break;
                                case "GREEN":
                                    plus_color = "§a+";
                                    break;
                                case "YELLOW":
                                    plus_color = "§e+";
                                    break;
                                case "LIGHT_PURPLE":
                                    plus_color = "§d+";
                                    break;
                                case "WHITE":
                                    plus_color = "§f+";
                                    break;
                                case "BLUE":
                                    plus_color = "§9+";
                                    break;
                                case "DARK_GREEN":
                                    plus_color = "§2+";
                                    break;
                                case "DARK_RED":
                                    plus_color = "§4+";
                                    break;
                                case "DARK_AQUA":
                                    plus_color = "§3+";
                                    break;
                                case "DARK_PINK":
                                    plus_color = "§5+";
                                    break;
                                case "DARK_GRAY":
                                    plus_color = "§8+";
                                    break;
                                case "BLACK":
                                    plus_color = "§0+";
                                    break;
                                case "DARK_BLUE":
                                    plus_color = "§1+";
                                    break;
                            }

                            String prefix = "§b[MVP" + plus_color + "§b] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.AQUA);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString());
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");
                        }
                        if (getFieldOrNA("PackageRank", player).toString().equalsIgnoreCase("MVP")) {

                            String prefix = "§b[MVP] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.AQUA);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");

                        }
                        if (getFieldOrNA("PackageRank", player).toString().equalsIgnoreCase("VIP_PLUS")) {

                            String prefix = "§a[VIP§e+§a] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.GREEN);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");

                        }
                        if (getFieldOrNA("PackageRank", player).toString().equalsIgnoreCase("VIP")) {

                            String prefix = "§a[VIP] ";
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(prefix);
                            event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.GREEN);
                            event.getPlayer().setDisplayName(prefix+event.getPlayer().getName().toString()+"§f");

                        }
                    }
                }
            }
            if (getFieldOrNA("rank", player) != null) {
                if (getFieldOrNA("prefix", player) == null) {
                    String rank = getFieldOrNA("rank", player).toString();

                    String staffPrefix = "";
                    ChatColor staffColor = ChatColor.AQUA;

                    switch (rank) {

                        case "HELPER":
                            staffPrefix = "§9[HELPER] ";
                            staffColor = ChatColor.BLUE;
                            break;
                        case "MODARATOR":
                            staffPrefix = "§2[MODARATOR] ";
                            staffColor = ChatColor.DARK_GREEN;
                            break;
                        case "ADMIN":
                            staffPrefix = "§c[ADMIN] ";
                            staffColor = ChatColor.RED;
                            break;
                        case "YOUTUBER":
                            staffPrefix = "§c[§fYOUTUBE§c] ";
                            staffColor = ChatColor.RED;
                    }
                    event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(staffPrefix);
                    event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(staffColor);
                    event.getPlayer().setDisplayName(staffPrefix+event.getPlayer().getName().toString()+"§f");
                }
                if (getFieldOrNA("prefix", player) != null) {
                    event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix(getFieldOrNA("prefix", player) + " ");
                    if (event.getPlayer().getName().equalsIgnoreCase("technoblade")) {
                        event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.LIGHT_PURPLE);
                        event.getPlayer().setDisplayName(getFieldOrNA("prefix", player) + " " +event.getPlayer().getName().toString()+"§f");
                    }
                    event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.RED);
                    event.getPlayer().setDisplayName(getFieldOrNA("prefix", player) + " " +event.getPlayer().getName().toString()+"§f");
                }

            }
            if (getFieldOrNA("rank", player) == null) {
                if (getFieldOrNA("newPackageRank", player) == null) {
                    if (getFieldOrNA("PackageRank", player) == null) {
                        event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setPrefix("§7");
                        event.getPlayer().getScoreboard().getTeam(event.getPlayer().getName()).setColor(ChatColor.GRAY);
                        event.getPlayer().setDisplayName("§7"+event.getPlayer().getName().toString());
                    }
                }
            }
        });
    }
   private static String getFieldOrNA(String field, JsonObject json) {
        JsonElement value = json.get(field);
        if (value != null) {
            //If the field was found, return its value
            return value.getAsString();
       } else {
            //Otherwise, return "N/A"
           return null;
        }
   }
   @EventHandler
    public void chatFormats(AsyncPlayerChatEvent chat) {

        chat.setFormat(chat.getPlayer().getDisplayName() + ": " + chat.getMessage());
   }
   @EventHandler
    public void leaveMessage(PlayerQuitEvent quit) {
       quit.setQuitMessage(quit.getPlayer().getScoreboard().getTeam(quit.getPlayer().getName()).getPrefix() + quit.getPlayer().getName() + " §eLeft the game.");
   }
}