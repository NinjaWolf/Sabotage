package com.github.NinjaWolf.Sabotage.Listeners;

import com.github.NinjaWolf.Sabotage.Handlers.SignHandler;
import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;
import com.github.NinjaWolf.Sabotage.Utils.Permissions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;


public class BlockListener implements Listener {

    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        String[] line = event.getLines();
        
        if (!event.getLine(0).equalsIgnoreCase("[Sabotage]"))
            return;
        
        if (Permissions.hasPermission(player, Permissions.ADMINISTRATION)) {
            if (line[1].equalsIgnoreCase("Join") && line[2].isEmpty() && line[3].isEmpty()
                    || line[2].equalsIgnoreCase("Join") && line[1].isEmpty() && line[3].isEmpty()
                    || line[3].equalsIgnoreCase("Join") && line[1].isEmpty() && line[2].isEmpty()) {
                
                SignHandler.getInstance().createJoinGameSign(player, event);
                return;
            } else if (line[1].equalsIgnoreCase("Leave") && line[2].isEmpty() && line[3].isEmpty()
                    || line[2].equalsIgnoreCase("Leave") && line[1].isEmpty() && line[3].isEmpty()
                    || line[3].equalsIgnoreCase("Leave") && line[1].isEmpty() && line[2].isEmpty()) {
                
                SignHandler.getInstance().createLeaveGameSign(player, event);
                return;
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        if (Permissions.hasPermission(player, Permissions.ADMINISTRATION))
            return;
        
        if (TeamsHandler.getInstance().isInGame(player)) {
            if (!block.getType().equals(Material.LADDER)) {
                event.setCancelled(true);
            }
        } else
            event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        if (Permissions.hasPermission(player, Permissions.ADMINISTRATION))
            return;
        
        if (TeamsHandler.getInstance().isInGame(player)) {
            if (!block.getType().equals(Material.LADDER)) {
                event.setCancelled(true);
            }
        } else
            event.setCancelled(true);
    }
}
