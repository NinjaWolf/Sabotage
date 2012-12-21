package com.github.NinjaWolf.Sabotage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Configuration {
    
    private static Configuration instance = new Configuration();
    private static Plugin plugin;
    private FileConfiguration bombConfig = null;
    public File bombs = null;
    private FileConfiguration arenaConfig = null;
    public File arenas = null;
    
    public static Configuration getInstance() {
        return instance;
    }
    
    public void init(Plugin p)
    {
      plugin = p;

      p.getConfig().options().copyDefaults(true);
      p.saveDefaultConfig();

      this.arenas = new File(plugin.getDataFolder(), "arenas.yml");
      this.bombs = new File(plugin.getDataFolder(), "bomb.yml");
      try {
        if (!this.arenas.exists())
          this.arenas.createNewFile();
        if (!this.bombs.exists())
          this.bombs.createNewFile(); 
      } catch (Exception localException) {  }

      reloadArenas();
      reloadBombs();
    }
    
    public void reloadBombs() {
        if (bombs == null) {
        bombs = new File(plugin.getDataFolder(), "bomb.yml");
        }
        bombConfig = YamlConfiguration.loadConfiguration(bombs);

        InputStream defConfigStream = plugin.getResource("bomb.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            bombConfig.setDefaults(defConfig);
        }
    }
    
    public void reloadArenas() {
        arenaConfig = YamlConfiguration.loadConfiguration(arenas);
        
        InputStream defConfigStream = plugin.getResource("arenas.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            arenaConfig.setDefaults(defConfig);
        }
    }
    
    public FileConfiguration getBombConfig() {
        if (bombConfig == null) {
            this.reloadBombs();
        }
        return bombConfig;
    }
    
    public FileConfiguration getArenaConfig() {
        if(arenaConfig == null) {
            this.reloadArenas();
        }
        return arenaConfig;
    }
    
    public void saveBombs() {
        if (bombConfig == null || bombs == null) {
        return;
        }
        try {
            getBombConfig().save(bombs);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + bombs, ex);
        }
    }
    
    public void saveArenas() {
        if (arenaConfig == null || arenas == null) {
            return;
        }
        try {
            getArenaConfig().save(arenas);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + arenas, ex);
        }
    }
}
