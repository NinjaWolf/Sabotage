package com.github.NinjaWolf.Sabotage;

import java.io.File;

public class Configuration {
    
    public Sabotage plugin;
    public final File                   mainConfig    = new File(plugin.getDataFolder(), "config.yml");
    
    public void save() {
        plugin.saveConfig();
    }
    
    public void load()
    {
        plugin.reloadConfig();
    }
    
    public Configuration(Sabotage instance) {
        plugin = instance;
    }
}
