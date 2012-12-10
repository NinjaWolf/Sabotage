package com.github.NinjaWolf.Sabotage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration {
    
    public Sabotage plugin;
    
    private FileConfiguration bombConfig = null;
    private File bombConfigFile = null;
    
    public double BOMB_X;
    public double BOMB_Y;
    public double BOMB_Z;
    
    public void save() {
        plugin.saveConfig();
        this.saveBombConfig();
    }
    
    public void load() {
        plugin.reloadConfig();
        this.reloadBombConfig();
        
        BOMB_X = getBombConfig().getDouble("Bomb.X");
        BOMB_Y = getBombConfig().getDouble("Bomb.Y");
        BOMB_Z = getBombConfig().getDouble("Bomb.Z");
    }
    
    public void reloadBombConfig() {
        if (bombConfigFile == null) {
        bombConfigFile = new File(plugin.getDataFolder(), "bomb.yml");
        }
        bombConfig = YamlConfiguration.loadConfiguration(bombConfigFile);

        InputStream defConfigStream = plugin.getResource("bomb.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            bombConfig.setDefaults(defConfig);
        }
    }
    
    public FileConfiguration getBombConfig() {
        if (bombConfig == null) {
            this.reloadBombConfig();
        }
        return bombConfig;
    }
    
    public void saveBombConfig() {
        if (bombConfig == null || bombConfigFile == null) {
        return;
        }
        try {
            getBombConfig().save(bombConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + bombConfigFile, ex);
        }
    }
    
    public Configuration(Sabotage instance) {
        plugin = instance;
    }
}
