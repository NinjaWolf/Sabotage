package com.github.NinjaWolf.Sabotage.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
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

import com.github.NinjaWolf.Sabotage.Handlers.Teams;
import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;
import com.github.NinjaWolf.Sabotage.Utils.Permissions;


public class PlayerListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        ChatColor green = ChatColor.GREEN;
        
        TeamsHandler.getInstance().addToLobby(player);

        player.sendMessage(green + "Welcome " + displayName + ",");
        player.sendMessage(green + "You are in the Lobby. Play Nice, and Have Fun!");
        
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Sign sign = null;
        
        if (block == null)
            return;
        
        if (block.getType().equals(Material.SIGN) 
         || block.getType().equals(Material.SIGN_POST) 
         || block.getType().equals(Material.WALL_SIGN)) {
            
            if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                return;
            
                sign = (Sign) block.getState();
            
                if (!sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "  [Sabotage]  "))
                    return;
                
                
                if (Permissions.hasPermission(player, Permissions.JOIN)) {
                    if (sign.getLine(2).equalsIgnoreCase(ChatColor.GREEN + " [Join Game]")) {
                        TeamsHandler.getInstance().joinGame(player);
                        event.setUseInteractedBlock(Result.DENY);
                    }
                }

                if (Permissions.hasPermission(player, Permissions.LEAVE)) {
                    if (sign.getLine(2).equalsIgnoreCase(ChatColor.GREEN + " [Leave Game]")) {
                        TeamsHandler.getInstance().leaveGame(player);
                        event.setUseInteractedBlock(Result.DENY);
                    }
                }
            }
        }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        
        if (TeamsHandler.getInstance().isInGame(player)) {
            Bukkit.broadcastMessage(displayName + ChatColor.YELLOW + " has Left the Game.");
        }
        TeamsHandler.getInstance().removeFromTeam(player);
        
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Bukkit.getServer().getWorld("world").getSpawnLocation());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
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
            
            if (Teams.getInstance().inLobby(player.getName())) {
                event.setCancelled(true);
                attacker.sendMessage(green + "You Cannot attack a player in the Lobby!");
                return;
            }
            else if (Teams.getInstance().inLobby(attacker.getName())) {
                event.setCancelled(true);
                attacker.sendMessage(green + "You Cannot attack while in the Lobby!");
                return;
            }
            
            if ((Teams.getInstance().inRedTeam(attacker.getName()) && Teams.getInstance().inRedTeam(player.getName()))
             || (Teams.getInstance().inBlueTeam(attacker.getName()) && Teams.getInstance().inBlueTeam(player.getName()))) {
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
                
                if (Teams.getInstance().inLobby(player.getName())) {
                    event.setCancelled(true);
                    shooter.sendMessage(green + "You Cannot attack a player in the Lobby!");
                    return;
                }
                else if (Teams.getInstance().inLobby(shooter.getName())) {
                    event.setCancelled(true);
                    shooter.sendMessage(green + "You Cannot attack while in the Lobby!");
                    return;
                }
                
                if ((Teams.getInstance().inRedTeam(shooter.getName()) && Teams.getInstance().inRedTeam(player.getName()))
                        || (Teams.getInstance().inBlueTeam(shooter.getName()) && Teams.getInstance().inBlueTeam(player.getName()))) {
                    event.setCancelled(true);
                    shooter.sendMessage(green + "PvP is Disabled Between Teammates!");
                }
            }
        }
    }
}
