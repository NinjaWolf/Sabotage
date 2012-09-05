package com.github.NinjaWolf.Sabotage.Listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class PlayerListener implements Listener {
    
    private final Sabotage plugin;
    
    public PlayerListener(Sabotage instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String realName = player.getName();
        Bukkit.getServer().getWorld("sabotage").setSpawnLocation(0, 66, 0);
        Location lobby = Bukkit.getServer().getWorld("sabotage").getSpawnLocation();
        
        plugin.Teams.put(realName, "Lobby");
        player.teleport(lobby);
        event.setJoinMessage(ChatColor.DARK_PURPLE + "You are in the Lobby. Play Nice, and Have Fun!");
    
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        String Name = player.getName();
        
        if (plugin.Teams.get(player.getName()) != "leave")
            Bukkit.broadcastMessage(displayName + " Left the Game and has been moved back to the Lobby");
        
        plugin.Teams.remove(Name);
        event.setQuitMessage(displayName + " has Left the game");
        
        if (plugin.Teams.get(Name) != null)
            plugin.getLogger().log(Level.INFO, "Holy Shit Batman, The onPlayerQuit function dun Goofed, Tell the Developer ASAP!");
    }
}
