package com.github.NinjaWolf.Sabotage.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Permissions;
import com.github.NinjaWolf.Sabotage.Handlers.Teams;
import com.github.NinjaWolf.Sabotage.Handlers.SignHandler;
import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;

public class BlockListener implements Listener {
    Sabotage Main;
    Teams Tm;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        String[] line = event.getLines();

        if (!event.getLine(0).equalsIgnoreCase("[Sabotage]"))
            return;
        
        if (Permissions.hasPermission(player, Permissions.ADMINISTRATION)) {
            if (line[1].equalsIgnoreCase("Join") && line[2].isEmpty() && line[3].isEmpty()
            ||  line[2].equalsIgnoreCase("Join") && line[1].isEmpty() && line[3].isEmpty()
            ||  line[3].equalsIgnoreCase("Join") && line[1].isEmpty() && line[2].isEmpty()) {

            	SignHandler.getInstance().createJoinGameSign(player, event);
                return;
            } else
            	if (line[1].equalsIgnoreCase("Leave") && line[2].isEmpty() && line[3].isEmpty()
                ||  line[2].equalsIgnoreCase("Leave") && line[1].isEmpty() && line[3].isEmpty()
                ||  line[3].equalsIgnoreCase("Leave") && line[1].isEmpty() && line[2].isEmpty()) {
            		
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
            if (!(block.getType() == Material.LADDER)) {
                event.setCancelled(true);
            }
            }
                event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        if (Permissions.hasPermission(player, Permissions.ADMINISTRATION))
            return;
        
        if (TeamsHandler.getInstance().isInGame(player)) {
            if (!(block.getType() == Material.LADDER)) {
                event.setCancelled(true);
            }
            }
                event.setCancelled(true);
    }
    
    
    public BlockListener(Sabotage instance) {
        Main = instance;
    }
    
    public BlockListener(Teams instance) {
        Tm = instance;
    }
    
}