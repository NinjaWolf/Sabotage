package com.github.NinjaWolf.Sabotage.Handlers;

import net.minecraft.server.v1_4_5.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_4_5.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class BombHandler {
    static Sabotage Plugin;
    private static CraftItemStack craftStack;
    private static net.minecraft.server.v1_4_5.ItemStack itemStack;
    static BombHandler            instance = new BombHandler(Plugin);
    
    public static BombHandler getInstance() {
        return instance;
    }
    
    public void handleBombCapture(Block block, final Player player) {
        block.setType(Material.AIR);
        player.getInventory().addItem(new ItemStack(setName(new ItemStack(Material.OBSIDIAN), "Bomb")));
        player.setMetadata("Carrier", new FixedMetadataValue(Plugin, true));
        

        Plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin, new Runnable() {
            public void run() {
                for (Player players: Bukkit.getOnlinePlayers()) {
                    players.setCompassTarget(player.getLocation());
                }
            }
        }, 5 * 20, 5 * 20);
        Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.GREEN + "Grabbed the Bomb!");
    }
    
    public boolean isBomb(Block block) {
        if (!block.hasMetadata("Bomb")) {
            return false;
        }
        return this.getBomb().equals(block.getLocation());
    }
    
    public Location getBomb() {
        return new Location(
                Plugin.getServer().getWorlds().get(0),
                Plugin.config.BOMB_X,
                Plugin.config.BOMB_Y,
                Plugin.config.BOMB_Z);
    }
    
    public static ItemStack setName(ItemStack item, String name) {
        if (item instanceof CraftItemStack) {
        craftStack = (CraftItemStack) item;
        itemStack = craftStack.getHandle();
        }
        else if (item instanceof ItemStack) {
        craftStack = new CraftItemStack(item);
        itemStack = craftStack.getHandle();
        }
        NBTTagCompound tag = itemStack.tag;
        if (tag == null) {
        tag = new NBTTagCompound();
        tag.setCompound("display", new NBTTagCompound());
        itemStack.tag = tag;
        }
         
        tag = itemStack.tag.getCompound("display");
        tag.setString("Name", name);
        itemStack.tag.setCompound("display§r", tag);
        return craftStack;
        }
    
    public static String getName(ItemStack item) {
        if (item instanceof CraftItemStack) {
        craftStack = (CraftItemStack) item;
        itemStack = craftStack.getHandle();
        }
        else if (item instanceof ItemStack) {
        craftStack = new CraftItemStack(item);
        itemStack = craftStack.getHandle();
        }
        NBTTagCompound tag = itemStack.tag;
        if (tag == null) {
        return null;
        }
        tag = itemStack.tag.getCompound("display");
        return tag.getString("Name");
        }
    
    public BombHandler(Sabotage instance) {
        Plugin = instance;
    }
}
