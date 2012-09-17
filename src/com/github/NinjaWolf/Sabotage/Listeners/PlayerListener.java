package com.github.NinjaWolf.Sabotage.Listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
        ChatColor purple = ChatColor.LIGHT_PURPLE;
        
        Bukkit.getServer().getWorld("world").setSpawnLocation(0, 66, 0);
        Location Lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        
        plugin.Teams.put("Lobby", Name);
        player.teleport(Lobby);
        player.sendMessage(purple + "Welcome " + displayName + ",");
        player.sendMessage(purple + "You are in the Lobby. Play Nice, and Have Fun!");
        
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        String getTeam = plugin.Teams.get(player.getName());
        
        if (plugin.inLobby(player.getName()) == false) {
            Bukkit.broadcastMessage(displayName + " Left the Game and has been moved back to the Lobby");
        }
        plugin.Teams.remove(player.getName());
        event.setQuitMessage(displayName + " has Left the game");
        
        if (getTeam != null) {
            plugin.getLogger().log(Level.WARNING, "Holy Shit Batman, The onPlayerQuit function dun Goofed, Tell the Developer ASAP!");
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Location Lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        event.setRespawnLocation(Lobby);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if ((event.getDamager() instanceof Player)) {
            Player attacker = (Player) event.getDamager();
            Player player = (Player) event.getEntity();
            ChatColor red = ChatColor.RED;
            ChatColor blue = ChatColor.BLUE;
            ChatColor green = ChatColor.GREEN;
            
            if (plugin.inLobby(player.getName()) == true || plugin.inLobby(attacker.getName()) == true) {
                event.setCancelled(true);
                attacker.sendMessage(green + "PvP is Disabled in the Lobby!");
                return;
            }
            
            if (plugin.inRedTeam(attacker.getName()) == true && plugin.inRedTeam(player.getName()) == true) {
                event.setCancelled(true);
                attacker.sendMessage(red + "PvP is Disabled Between Teammates!");
            } else if (plugin.inBlueTeam(attacker.getName()) == true && plugin.inBlueTeam(player.getName()) == true) {
                event.setCancelled(true);
                attacker.sendMessage(blue + "PvP is Disabled Between Teammates!");
        }
        }
        else if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player player = (Player) event.getEntity();
                Player shooter = (Player) arrow.getShooter();
                ChatColor red = ChatColor.RED;
                ChatColor blue = ChatColor.BLUE;
                ChatColor green = ChatColor.GREEN;
                
            if (plugin.inLobby(player.getName()) == true || plugin.inLobby(shooter.getName()) == true) {
                event.setCancelled(true);
                shooter.sendMessage(green + "PvP is Disabled in the Lobby!");
                return;
            }
                
            if (plugin.inRedTeam(shooter.getName()) == true && plugin.inRedTeam(player.getName()) == true) {
                event.setCancelled(true);
                shooter.sendMessage(red + "PvP is Disabled Between Teammates!");
            } else if (plugin.inBlueTeam(shooter.getName()) == true && plugin.inBlueTeam(player.getName()) == true) {
                event.setCancelled(true);
                shooter.sendMessage(blue + "PvP is Disabled Between Teammates!");
        }
                }
            }
        }
    }