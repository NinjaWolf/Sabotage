package com.github.NinjaWolf.Sabotage.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class BlockListener implements Listener {
    Sabotage plugin;
    
    public BlockListener(Sabotage instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (plugin.Teams.get(player.getName()) != "Lobby") {
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
        if (plugin.Teams.get(player.getName()) != "Lobby") {
            if (!(block.getType() == Material.LADDER)) {
                event.setCancelled(true);
            }
            }
                event.setCancelled(true);
        
    }
    
}
