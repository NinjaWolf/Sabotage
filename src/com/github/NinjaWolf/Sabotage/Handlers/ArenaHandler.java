package com.github.NinjaWolf.Sabotage.Handlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Utils.Game;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;


public class ArenaHandler {
    private Sabotage     plugin;
    
    public ArenaHandler(Sabotage Plugin) {
         plugin = Plugin;

    }

    public void createArena(Player player, String name) {
        
        WorldEditPlugin we = plugin.getWorldEdit();
        Selection sel = we.getSelection(player);
        if (sel == null) {
            player.sendMessage(ChatColor.RED + "You must make a WorldEdit Selection first");
            return;
        }
        Location max = sel.getMaximumPoint();
        Location min = sel.getMinimumPoint();
        
        int no = plugin.config.ARENA_NO;
        plugin.config.getArenaConfig().addDefault("Sabotage.Arena.No", Integer.valueOf(no));
        if (plugin.gameManager.games.size() == 0) {
            no = 1;
        } else
            no = ((Game)plugin.gameManager.games.get(plugin.gameManager.games.size() - 1)).getID() + 1;
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + name + ".world", max.getWorld().getName());
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + name + ".x1", Integer.valueOf(max.getBlockX()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + name + ".y1", Integer.valueOf(max.getBlockY()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + name + ".z1", Integer.valueOf(max.getBlockZ()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + name + ".x2", Integer.valueOf(min.getBlockX()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + name + ".y2", Integer.valueOf(min.getBlockY()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + name + ".z2", Integer.valueOf(min.getBlockZ()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + name + ".enabled", Boolean.valueOf(true));
        plugin.config.getArenaConfig().set("Sabotage.Spawns." + name, null);
        
        plugin.config.saveArenaConfig();
        
        addArena(no, name);
        //plugin.bombHandler.spawnBomb(no); NPE. Fix Pronto.

        player.sendMessage(ChatColor.GREEN + "Arena " + name + " Succesfully added");
    }
    
    public void addArena(int no, String arenaName) {
        Game game = new Game(no);
        plugin.gameManager.gameInfo.put(game, arenaName);
    }
    
    public void removeArena(int no) {
        //TODO: add a command to delete arenas.
    }
}
