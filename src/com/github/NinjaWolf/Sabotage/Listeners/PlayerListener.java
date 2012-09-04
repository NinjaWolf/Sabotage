package com.github.NinjaWolf.Sabotage.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class PlayerListener implements Listener {
    
    private Sabotage plugin;
    
    public PlayerListener(Sabotage instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) { 
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        String realName = player.getName();
        

        plugin.Teams.put(realName, "Lobby");
        player.teleport(lobby);
        player.sendMessage("Welcome " + displayName + "! You are in the Lobby. Please join a Team, and Have Fun!");
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        String Name = player.getName();
        
        plugin.Teams.remove(Name);
        event.setQuitMessage(displayName +" has Left the game");
    }
}
