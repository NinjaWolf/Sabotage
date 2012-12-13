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
    private FileConfiguration arenaConfig = null;
    private File arenaConfigFile = null;
    
    public double BOMB_X;
    public double BOMB_Y;
    public double BOMB_Z;
    
    public int ARENA_NO;
    public int ARENA;
    
    public void save() {
        plugin.saveConfig();
        this.saveBombConfig();
        this.saveArenaConfig();
    }
    
    public void load() {
        plugin.reloadConfig();
        this.reloadBombConfig();
        this.reloadArenaConfig();
        
        BOMB_X = getBombConfig().getDouble("Sabotage.Arenas.Bomb.X");
        BOMB_Y = getBombConfig().getDouble("Arena.Test.Bomb.Y");
        BOMB_Z = getBombConfig().getDouble("Arena.Test.Bomb.Z");
        
        ARENA_NO = getArenaConfig().getInt("Sabotage.Arenano");
        ARENA    = getArenaConfig().getInt("Sabotage.Arenas");
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
    
    public void reloadArenaConfig() {
        if(arenaConfigFile == null) {
            arenaConfigFile = new File(plugin.getDataFolder(), "arenas.yml");
        }
        arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
        
        InputStream defConfigStream = plugin.getResource("arenas.yml");
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
    
    public FileConfiguration getArenaConfig() {
        if(arenaConfig == null) {
            this.reloadArenaConfig();
        }
        return arenaConfig;
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
    
    public void saveArenaConfig() {
        if (arenaConfig == null || arenaConfigFile == null) {
            return;
        }
        try {
            getArenaConfig().save(arenaConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + arenaConfigFile, ex);
        }
    }
    
    public Configuration(Sabotage instance) {
        plugin = instance;
    }
}
