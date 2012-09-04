package com.github.NinjaWolf.Sabotage;


public class Configuration {
    
    private final Sabotage plugin;
    
    public Configuration(Sabotage instance) {
        plugin = instance;
    }
    
    public void save() {
        plugin.saveConfig();
    }
    
    public void load()
    {
        plugin.reloadConfig();
    }
}
