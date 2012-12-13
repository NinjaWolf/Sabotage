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
        if (plugin.gameManager.games.size() == 0) {
            no = 1;
        } else
            no = ((Game)plugin.gameManager.games.get(plugin.gameManager.games.size() - 1)).getID() + 1;
        plugin.config.getArenaConfig().set("Sabotage.Arenas.No", Integer.valueOf(no));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + no + ".world", max.getWorld().getName());
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + no + ".x1", Integer.valueOf(max.getBlockX()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + no + ".y1", Integer.valueOf(max.getBlockY()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + no + ".z1", Integer.valueOf(max.getBlockZ()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + no + ".x2", Integer.valueOf(min.getBlockX()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + no + ".y2", Integer.valueOf(min.getBlockY()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + no + ".z2", Integer.valueOf(min.getBlockZ()));
        plugin.config.getArenaConfig().set("Sabotage.Arenas." + no + ".enabled", Boolean.valueOf(true));
        plugin.config.getArenaConfig().set("Sabotage.Spawns." + no, null);
        
        plugin.config.saveArenaConfig();
        
        plugin.bombHandler.spawnBomb(no);
        addArena(no, name);

        player.sendMessage(ChatColor.GREEN + "Arena " + name + "(" + no + ") Succesfully added");
    }
    
    public void addArena(int no, String arenaName) {
        Game game = new Game(no);
        plugin.gameManager.games.add(game);
        plugin.gameManager.gameInfo.put(no, arenaName);

    }
    
    public void removeArena(int no) {
        //TODO: add a command to delete arenas.
    }
}
