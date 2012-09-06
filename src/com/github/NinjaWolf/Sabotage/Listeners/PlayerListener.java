package com.github.NinjaWolf.Sabotage.Listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
//import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
        String Name = player.getName();
        String getTeam = plugin.Teams.get(Name);
        
        if (getTeam != "Lobby") {
            Bukkit.broadcastMessage(displayName + " Left the Game and has been moved back to the Lobby");
        }
        
        plugin.Teams.remove(Name);
        event.setQuitMessage(displayName + " has Left the game");
        
        if (getTeam != null) {
            plugin.getLogger().log(Level.WARNING, "Holy Shit Batman, The onPlayerQuit function dun Goofed, Tell the Developer ASAP!");
        }
    }
}
    /** Spams Console, line 64
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        Player Attacker = (Player) event.getDamager();
        Player player = (Player) event.getEntity();
        String redTeam = plugin.Teams.get("Red");
        String blueTeam = plugin.Teams.get("Blue");
        String Killer = Attacker.getName();
        String Name = player.getName();
        String getTeam = plugin.Teams.get(Name);
        
        if (Attacker instanceof Player)
            if ((getTeam != "Red") || (getTeam != "Blue")) {
                event.setCancelled(true);
            }
        
        if (redTeam.contains(Killer) && redTeam.contains(Name)) {
            event.setCancelled(true);
        } else if (blueTeam.contains(Killer) && blueTeam.contains(Name)) {
            event.setCancelled(true);
        }
        
    }
}
*/