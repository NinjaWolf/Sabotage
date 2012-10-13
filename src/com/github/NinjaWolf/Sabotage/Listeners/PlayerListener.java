package com.github.NinjaWolf.Sabotage.Listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Handlers.Teams;

public class PlayerListener implements Listener {
    Sabotage Main;
    Teams Tm;
    
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String Name = player.getName();
        String displayName = player.getDisplayName();
        ChatColor purple = ChatColor.LIGHT_PURPLE;
        
        Bukkit.getServer().getWorld("world").setSpawnLocation(10, 66, 10);
        Location Lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        
        Main.Teams.put("Lobby", Name);
        player.teleport(Lobby);
        player.sendMessage(purple + "Welcome " + displayName + ",");
        player.sendMessage(purple + "You are in the Lobby. Play Nice, and Have Fun!");
        
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
    	Player	player = event.getPlayer();
    	
    	if(!(event.getAction() == Action.RIGHT_CLICK_BLOCK))
    		return;
    	
    	if (event.getMaterial() == Material.SIGN || event.getMaterial() == Material.SIGN_POST) {
    		player.playEffect(EntityEffect.HURT);
    	}
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        String getTeam = Main.Teams.get(player.getName());
        
        if (Main.inLobby(player.getName()) == false) {
            Bukkit.broadcastMessage(displayName + " Left the Game and has been moved back to the Lobby");
        }
        Main.Teams.remove(player.getName());
        event.setQuitMessage(displayName + " has Left the game.");
        
        if (getTeam != null) {
            Main.getLogger().log(Level.WARNING, "Holy Shit Batman, The onPlayerQuit function dun Goofed, Tell the Developer ASAP!");
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Location Lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        event.setRespawnLocation(Lobby);
    }
    
    @EventHandler(priority= EventPriority.HIGHEST, ignoreCancelled= true)
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        String getTeam = Main.Teams.get(player.getName());
        
        Main.Teams.remove(player.getName());
        event.setLeaveMessage(displayName + " was Kicked from the game.");
        
        if (getTeam != null) {
            Main.getLogger().log(Level.WARNING, "Holy Shit Batman, The onPlayerKick function dun Goofed, Tell the Developer ASAP!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || (event.isCancelled()))
            return;

        if ((event.getDamager() instanceof Player)) {
            Player attacker = (Player) event.getDamager();
            Player player = (Player) event.getEntity();
            ChatColor green = ChatColor.GREEN;
            
	            if (Main.inLobby(player.getName()) == true) {
	                event.setCancelled(true);
	                attacker.sendMessage(green + "You Cannot attack a player in the Lobby!");
	            } 
	            else if (Main.inLobby(attacker.getName()) == true) {
	            	event.setCancelled(true);
	            	attacker.sendMessage(green + "You Cannot attack while in the Lobby!");
	            }
	            
	            if ((Main.inRedTeam(attacker.getName()) == true && Main.inRedTeam(player.getName()) == true)
	            || (Main.inBlueTeam(attacker.getName()) == true && Main.inBlueTeam(player.getName()) == true)) {
	                event.setCancelled(true);
	                attacker.sendMessage(green + "PvP is Disabled Between Teammates!");
            	}
	        }
        else if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player player = (Player) event.getEntity();
                Player shooter = (Player) arrow.getShooter();
                ChatColor green = ChatColor.GREEN;
                
	            if (Main.inLobby(player.getName()) == true) {
	                event.setCancelled(true);
	                shooter.sendMessage(green + "You Cannot attack a player in the Lobby!");
	            } 
	            else if (Main.inLobby(shooter.getName()) == true) {
	            	event.setCancelled(true);
	            	shooter.sendMessage(green + "You Cannot attack while in the Lobby!");
	            }
                
	            if ((Main.inRedTeam(shooter.getName()) == true && Main.inRedTeam(player.getName()) == true)
	            || (Main.inBlueTeam(shooter.getName()) == true && Main.inBlueTeam(player.getName()) == true)) {
	                event.setCancelled(true);
	                shooter.sendMessage(green + "PvP is Disabled Between Teammates!");
            	}
                }
            }
        }
    
    public PlayerListener(Sabotage instance) {
        Main = instance;
    }
    
    public PlayerListener(Teams instance) {
        Tm = instance;
    }

    }