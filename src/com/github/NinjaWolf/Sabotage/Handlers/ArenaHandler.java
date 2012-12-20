package com.github.NinjaWolf.Sabotage.Handlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.NinjaWolf.Sabotage.Configuration;
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
        Configuration config = Configuration.getInstance();
        WorldEditPlugin we = plugin.getWorldEdit();
        Selection sel = we.getSelection(player);
        if (sel == null) {
            player.sendMessage(ChatColor.RED + "You must make a WorldEdit Selection first");
            return;
        }
        Location max = sel.getMaximumPoint();
        Location min = sel.getMinimumPoint();
        
        int no = config.getArenaConfig().getInt("Sabotage.Arenas.No");
        if (plugin.gameManager.games.size() == 0) {
            no = 1;
        } else
            no = ((Game)plugin.gameManager.games.get(plugin.gameManager.games.size() - 1)).getID() + 1;
        config.getArenaConfig().set("Sabotage.Arenas.No", Integer.valueOf(no));
        config.getArenaConfig().set("Sabotage.Arenas." + name + ".world", sel.getWorld().getName());
        config.getArenaConfig().set("Sabotage.Arenas." + name + ".x1", Integer.valueOf(max.getBlockX()));
        config.getArenaConfig().set("Sabotage.Arenas." + name + ".y1", Integer.valueOf(max.getBlockY()));
        config.getArenaConfig().set("Sabotage.Arenas." + name + ".z1", Integer.valueOf(max.getBlockZ()));
        config.getArenaConfig().set("Sabotage.Arenas." + name + ".x2", Integer.valueOf(min.getBlockX()));
        config.getArenaConfig().set("Sabotage.Arenas." + name + ".y2", Integer.valueOf(min.getBlockY()));
        config.getArenaConfig().set("Sabotage.Arenas." + name + ".z2", Integer.valueOf(min.getBlockZ()));
        config.getArenaConfig().set("Sabotage.Arenas." + name + ".enabled", Boolean.valueOf(true));
        config.getArenaConfig().set("Sabotage.Spawns." + name, null);
        
        config.saveArenas();
        
        plugin.bombHandler.spawnBomb(no, name);
        addArena(no, name);

        player.sendMessage(ChatColor.GREEN + "Arena " + name + " (" + no + ") Succesfully added");
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
