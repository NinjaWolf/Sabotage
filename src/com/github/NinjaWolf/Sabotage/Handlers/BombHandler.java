package com.github.NinjaWolf.Sabotage.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class BombHandler {
    static Sabotage Plugin;
    
    static BombHandler            instance = new BombHandler(Plugin);
    
    public static BombHandler getInstance() {
        return instance;
    }
    
    public void handleBombCapture(Block block, final Player player) {
        block.setType(Material.AIR);
        player.getInventory().addItem(new ItemStack(Material.OBSIDIAN));

        Plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin, new Runnable() {
            public void run() {
                for (Player players: Bukkit.getOnlinePlayers()) {
                    players.setCompassTarget(player.getLocation());
                }
            }
        }, 5 * 20, 5 * 20);
        Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.GREEN + "Grabbed the Bomb!");
    }
    
    public boolean isBomb(Location loc) {
        boolean test = true;
        if(test) {
        return true;
        }
        return false;
    }
    
    public BombHandler(Sabotage instance) {
        Plugin = instance;
    }
}
