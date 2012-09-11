package com.github.NinjaWolf.Sabotage.Listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class PlayerListener implements Listener {
    Sabotage plugin;
    
    public PlayerListener(Sabotage instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String Name = player.getName();
        String displayName = player.getDisplayName();
        ChatColor purple = ChatColor.DARK_PURPLE;
        
        Bukkit.getServer().getWorld("world").setSpawnLocation(0, 66, 0);
        Location lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        
        plugin.Teams.put(Name, "Lobby");
        player.teleport(lobby);
        player.sendMessage(purple + "Welcome " + displayName + ",");
        player.sendMessage(purple + "You are in the Lobby. Play Nice, and Have Fun!");
        
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        String getTeam = plugin.Teams.get(player.getName());
        
        if (getTeam != "Lobby") {
            Bukkit.broadcastMessage(displayName + " Left the Game and has been moved back to the Lobby");
        }
        
        plugin.Teams.remove(player.getName());
        event.setQuitMessage(displayName + " has Left the game");
        
        if (getTeam != null) {
            plugin.getLogger().log(Level.WARNING, "Holy Shit Batman, The onPlayerQuit function dun Goofed, Tell the Developer ASAP!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        String redTeam = plugin.Teams.get("Red");
        String blueTeam = plugin.Teams.get("Blue");
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if ((event.getDamager() instanceof Player)) {
            Player attacker = (Player) event.getDamager();
            Player player = (Player) event.getEntity();
            
            if ((redTeam.contains(player.getName()) || (blueTeam.contains(player.getName())))) {
                event.setCancelled(false);
            }
        
        if (!redTeam.contains(attacker.getName()) && !redTeam.contains(player.getName())) {
            event.setCancelled(false);
        } else if (!blueTeam.contains(attacker.getName()) && !blueTeam.contains(player.getName())) {
            event.setCancelled(false);
        }
        }
            event.setCancelled(true);
    }
}
