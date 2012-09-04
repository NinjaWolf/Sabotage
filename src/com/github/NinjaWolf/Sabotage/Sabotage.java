package com.github.NinjaWolf.Sabotage;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.NinjaWolf.Sabotage.Commands.Commands;
import com.github.NinjaWolf.Sabotage.Listeners.BlockListener;
import com.github.NinjaWolf.Sabotage.Listeners.PlayerListener;

public class Sabotage extends JavaPlugin {
    
    private final PlayerListener       playerListener = new PlayerListener(this);
    private final BlockListener        blockListener  = new BlockListener(this);
    public final Configuration         config        = new Configuration(this);
    public final HashMap<String, String> Teams = new HashMap<String, String>();
    public final File config_file = new File(getDataFolder(), "config.yml");
    public final PluginDescriptionFile pdfFile        = getDescription();
    
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        getCommand("sabotage").setExecutor(new Commands(this));

        if (!config_file.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        
        pm.registerEvents(playerListener, this);
        pm.registerEvents(blockListener, this);
        
        getLogger().log(Level.INFO, "[" + pdfFile.getName() + "]" + " Version: " + pdfFile.getVersion() + "is now Enabled.");
    }
    
    @Override
    public void onDisable() {
        
    }
}
