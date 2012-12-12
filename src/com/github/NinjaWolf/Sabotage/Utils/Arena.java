package com.github.NinjaWolf.Sabotage.Utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class Arena {
    Sabotage plugin;
 
    public World getWorld(int gameID) {
        String arena = plugin.gameManager.getName(gameID);
        String World = plugin.config.getArenaConfig().getString("Sabotage.Arenas." + arena + ".world");
        return plugin.getServer().getWorld(World);
    }
    
    public Location getCenterBlock(int gameID) {
        FileConfiguration c = plugin.config.getArenaConfig();
        String arena = "Sabotage.Arenas." + plugin.gameManager.getName(gameID);
        int x = c.getInt(arena + ".x1") - c.getInt(arena + ".x2");
        int y1 = c.getInt(arena + ".y1") + c.getInt(arena + ".y2");
        int y = y1 / 2;
        int z = c.getInt(arena + ".z1") - c.getInt(arena + ".z2");
        return new Location(getWorld(gameID), x, y, z);
    }
    
    public Arena(Sabotage Plugin) {
        plugin = Plugin;

   }
}
