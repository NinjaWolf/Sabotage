package com.github.NinjaWolf.Sabotage.Listeners;

import net.minecraft.server.v1_4_5.Packet62NamedSoundEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_4_5.entity.CraftPlayer;
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

import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Handlers.Teams;
import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;
import com.github.NinjaWolf.Sabotage.Utils.Permissions;


public class PlayerListener implements Listener {
    Sabotage plugin;
    
    @EventHandler(priority = EventPriority.LOW)
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
        
        if (block.getType().equals(Material.OBSIDIAN)) {
            if (!TeamsHandler.getInstance().isInGame(player)) {
                return;
            }
            
            if (!plugin.bombHandler.isBomb(block)) {
                return;
            }
            
            plugin.bombHandler.handleBombCapture(block, player);
            
            
            

            
        }
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();

        Bukkit.broadcastMessage(displayName + ChatColor.YELLOW + " has Left the Game.");
        TeamsHandler.getInstance().removePlayer(player);
        
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Bukkit.getServer().getWorld("world").getSpawnLocation());
    }
    
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        
        TeamsHandler.getInstance().removePlayer(player);
        event.setLeaveMessage(displayName + ChatColor.YELLOW + " was Kicked. (");
        
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
                Location sLoc = shooter.getLocation();
                ChatColor green = ChatColor.GREEN;
                
                if (Teams.getInstance().inLobby(player.getName())) {
                    event.setCancelled(true);
                    shooter.sendMessage(green + "You Cannot shoot a player in the Lobby!");
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
                    return;
                }
                
                if (TeamsHandler.getInstance().isInGame(shooter)) {
                    Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect("random.orb", sLoc.getX(), sLoc.getY(), sLoc.getZ(), 1.0F, 0.0F);
                    ((CraftPlayer)shooter).getHandle().netServerHandler.sendPacket(packet);
                }
            }
        }
    }
    
    public PlayerListener(Sabotage Plugin) {
        plugin = Plugin;
    }
}
