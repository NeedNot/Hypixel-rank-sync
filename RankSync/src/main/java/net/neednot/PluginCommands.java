package net.neednot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.neednot.RankSync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PluginCommands implements CommandExecutor, TabCompleter {

    RankSync plugin;

    public PluginCommands(RankSync pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (args.length < 1) return false;
        if (!args[0].equalsIgnoreCase("reload") && !args[0].equalsIgnoreCase("guild")) return false;
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Reloaded!");
            return true;
        }
        if (args.length <= 1) return false;
        if (!args[1].equalsIgnoreCase("rank")) return false;
        if (args.length <= 2) return false;
        if (!args[2].equalsIgnoreCase("true") && !args[2].equalsIgnoreCase("false")) return false;
        if (args[2].equalsIgnoreCase("true")) {
            plugin.getConfig().set("Show guild rank", true);
            plugin.saveConfig();
            sender.sendMessage("§aSetting changed and applied!");
            return true;
        }
        if (args[2].equalsIgnoreCase("false")) {

            plugin.getConfig().set("Show guild rank", false);
            plugin.saveConfig();
            sender.sendMessage("§aSetting changed and applied!");
            return true;
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        if (args.length<=1) {
            return Arrays.asList("reload", "guild");
        }
        if (args.length==2 && args[0].equals("guild")) {
            return Arrays.asList("rank");
        }
        if (args.length==3 && args[1].equals("rank")) {
            return Arrays.asList("true", "false");
        }
        if (args.length >=2 && args[0].equals("reload")) {
            return Arrays.asList("");
        }
        if (args.length >=4 && args[2].equals("true")) {
            return Arrays.asList("");
        }
        if (args.length >=4 && args[2].equals("false")) {
            return Arrays.asList("");
        }

        return null;
    }

}