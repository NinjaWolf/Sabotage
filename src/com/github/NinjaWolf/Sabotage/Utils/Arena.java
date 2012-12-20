package com.github.NinjaWolf.Sabotage.Utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.NinjaWolf.Sabotage.Configuration;
import com.github.NinjaWolf.Sabotage.Sabotage;

public class Arena {
    Sabotage plugin;
    Location min;
    Location max;

    public Arena(Location min, Location max) {
      this.max = max;
      this.min = min;
    }

    public World getWorld(String gameName) {
        Configuration.getInstance().reloadArenas();
        String World = Configuration.getInstance().getArenaConfig().getString("Sabotage.Arenas." + gameName + ".world");
        return plugin.getServer().getWorld(World);
    }
    
    public Location getCenterBlock(String name) {
        FileConfiguration c = Configuration.getInstance().getArenaConfig();
        Configuration.getInstance().reloadArenas();
        String arena = "Sabotage.Arenas." + name;
        int x = (c.getInt(arena + ".x1") + c.getInt(arena + ".x2")) / 2;
        int y = (c.getInt(arena + ".y1"));
        int z = (c.getInt(arena + ".z1") + c.getInt(arena + ".z2")) / 2;
        return new Location(getWorld(name), x, y, z);
    }
    
    public boolean containsBlock(Location v) {
      if (v.getWorld() != this.min.getWorld())
        return false;
      double x = v.getX();
      double y = v.getY();
      double z = v.getZ();

      return (x >= this.min.getBlockX()) && (x < this.max.getBlockX() + 1) && 
        (y >= this.min.getBlockY()) && (y < this.max.getBlockY() + 1) && 
        (z >= this.min.getBlockZ()) && (z < this.max.getBlockZ() + 1);
    }
    
    public int getBlockGameId(Location v) {
      for (Game g : plugin.gameManager.games) {
        if (g.isBlockInArena(v)) {
          return g.getID();
        }
      }
      return -1;
    }
    
    public Location getMax() {
      return this.max;
    }

    public Location getMin() {
      return this.min;
    }
    
    public Arena(Sabotage Plugin) {
        plugin = Plugin;

   }
}
