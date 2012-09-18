package com.github.NinjaWolf.Sabotage.Listeners;

import org.bukkit.ChatColor;
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
import com.github.NinjaWolf.Sabotage.Managers.Teams;

public class BlockListener implements Listener {
    Sabotage Main;
    Teams Tm;


    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        ChatColor green = ChatColor.GREEN;

        if (!event.getLine(0).equalsIgnoreCase("[Sabotage]"))
            return;
        
        if (Permissions.hasPermission(player, Permissions.ADMINISTRATION)) {
            if (event.getLine(1).equalsIgnoreCase("Join") && event.getLine(2).isEmpty() && event.getLine(3).isEmpty()
            ||  event.getLine(2).equalsIgnoreCase("Join") && event.getLine(1).isEmpty() && event.getLine(3).isEmpty()
            ||  event.getLine(3).equalsIgnoreCase("Join") && event.getLine(1).isEmpty() && event.getLine(2).isEmpty()) {

                event.setLine(0, green + "   [Sabotage]   ");
                event.setLine(1, null);
                event.setLine(2, green + "  [Join Game]   ");
                event.setLine(3, null);
                player.sendMessage(green + "[Sabotage] Game Sign Created Successfully!");
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
        
        if (!Main.inLobby(player.getName())) {
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
        
        if (!Main.inLobby(player.getName())) {
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