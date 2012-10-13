package com.github.NinjaWolf.Sabotage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.NinjaWolf.Sabotage.Generator.Arena_Generator;
import com.github.NinjaWolf.Sabotage.Listeners.BlockListener;
import com.github.NinjaWolf.Sabotage.Listeners.PlayerListener;

public class Sabotage extends JavaPlugin {
    
    private final PlayerListener         playerListener = new PlayerListener(this);
    private final BlockListener          blockListener  = new BlockListener(this);
    public final Configuration           config         = new Configuration(this);
    public final File                    config_file    = new File(getDataFolder(), "config.yml");
    
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        PluginDescriptionFile pdfFile = getDescription();
        
        if (!config_file.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        
        pm.registerEvents(playerListener, this);
        pm.registerEvents(blockListener, this);
        
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().log(Level.INFO, "Metrics Couldn't Be Loaded For Sabotage.");
        }
        
        getLogger().log(Level.INFO, "Version: " + pdfFile.getVersion() + " is now Enabled.");
    }
    
    @Override
    public void onDisable() {
        
        getLogger().log(Level.INFO, "Disabled.");
        
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new Arena_Generator();
    }
}
