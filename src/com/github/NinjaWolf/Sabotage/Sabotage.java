package com.github.NinjaWolf.Sabotage;

import java.io.File;
import java.util.HashMap;
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
    public final HashMap<String, String> Teams          = new HashMap<String, String>();
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
    
    public boolean inLobby(String playerName) {
        if (Teams.get("Lobby").contains(playerName)) { 
            return true;
        }
        return false;
    }
    
    public boolean inBlueTeam(String playerName) {
        if (Teams.get("Blue").contains(playerName)) { 
            return true;
        }
        return false;
    }
    
    public boolean inRedTeam(String playerName) {
        if (Teams.get("Red").contains(playerName)) { 
            return true;
        }
        return false;
    }
}
