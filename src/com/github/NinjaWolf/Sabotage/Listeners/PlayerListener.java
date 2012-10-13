package com.github.NinjaWolf.Sabotage.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
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

import com.github.NinjaWolf.Sabotage.Permissions;
import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Handlers.Teams;
import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;

public class PlayerListener implements Listener {
    Sabotage Main;
    
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        ChatColor purple = ChatColor.LIGHT_PURPLE;
        
        Bukkit.getServer().getWorld("world").setSpawnLocation(10, 66, 10);
        Location Lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        
        TeamsHandler.getInstance().addToLobby(player);
        player.teleport(Lobby);
        player.sendMessage(purple + "Welcome " + displayName + ",");
        player.sendMessage(purple + "You are in the Lobby. Play Nice, and Have Fun!");
        
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Sign sign = null;

		if (block == null)
			return;

		if (block.getTypeId() == 63 || block.getTypeId() == 68) {

			sign = (Sign) block.getState();

			if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
				return;
			}

			if (!sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "   [Sabotage]   ")) {
				return;
			}

			if (Permissions.hasPermission(player, Permissions.JOIN)) {
			if (sign.getLine(2).equalsIgnoreCase(ChatColor.GREEN + "  [Join Game]   ")) {
				TeamsHandler.getInstance().joinGame(player);
			}
		} else
			if (Permissions.hasPermission(player, Permissions.LEAVE))
				if (sign.getLine(2).equalsIgnoreCase(ChatColor.GREEN + "  [Leave Game]   ")) {
					TeamsHandler.getInstance().leaveGame(player);
			}
    	}
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        
        if (TeamsHandler.getInstance().isInGame(player)) {
            Bukkit.broadcastMessage(displayName + " Left the Game and has been moved back to the Lobby");
        }
        TeamsHandler.getInstance().removeFromTeam(player);
        event.setQuitMessage(displayName + " has Left the game.");

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
        
        TeamsHandler.getInstance().removeFromTeam(player);
        event.setLeaveMessage(displayName + " was Kicked from the game.");

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || (event.isCancelled()))
            return;

        if ((event.getDamager() instanceof Player)) {
            Player attacker = (Player) event.getDamager();
            Player player = (Player) event.getEntity();
            ChatColor green = ChatColor.GREEN;
            
	            if (Teams.getInstance().inLobby(player.getName()) == true) {
	                event.setCancelled(true);
	                attacker.sendMessage(green + "You Cannot attack a player in the Lobby!");
	                return;
	            } 
	            else if (Teams.getInstance().inLobby(attacker.getName()) == true) {
	            	event.setCancelled(true);
	            	attacker.sendMessage(green + "You Cannot attack while in the Lobby!");
	            	return;
	            }
	            
	            if ((Teams.getInstance().inRedTeam(attacker.getName()) == true && Teams.getInstance().inRedTeam(player.getName()) == true)
	            || (Teams.getInstance().inBlueTeam(attacker.getName()) == true && Teams.getInstance().inBlueTeam(player.getName()) == true)) {
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
                
	            if (Teams.getInstance().inLobby(player.getName()) == true) {
	                event.setCancelled(true);
	                shooter.sendMessage(green + "You Cannot attack a player in the Lobby!");
	                return;
	            } 
	            else if (Teams.getInstance().inLobby(shooter.getName()) == true) {
	            	event.setCancelled(true);
	            	shooter.sendMessage(green + "You Cannot attack while in the Lobby!");
	            	return;
	            }
                
	            if ((Teams.getInstance().inRedTeam(shooter.getName()) == true && Teams.getInstance().inRedTeam(player.getName()) == true)
	            || (Teams.getInstance().inBlueTeam(shooter.getName()) == true && Teams.getInstance().inBlueTeam(player.getName()) == true)) {
	                event.setCancelled(true);
	                shooter.sendMessage(green + "PvP is Disabled Between Teammates!");
            	}
                }
            }
        }
    
    public PlayerListener(Sabotage instance) {
        Main = instance;
    }
    }