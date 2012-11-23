package com.github.NinjaWolf.Sabotage;

public class Configuration {
    
    public Sabotage plugin;
    
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
