package net.neednot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.neednot.listeners.Sync;
import net.neednot.PluginCommands;

public final class RankSync extends JavaPlugin {

    private RankSync plugin;

    @Override
    public void onEnable() {

        plugin = this;


        FileConfiguration config = this.getConfig();
        config.addDefault("Show guild rank", true);
        config.addDefault("API key", "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new Sync(this), this);
        this.getCommand("rs").setExecutor(new PluginCommands(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
