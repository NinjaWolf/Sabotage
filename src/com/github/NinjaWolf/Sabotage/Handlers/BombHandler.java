package com.github.NinjaWolf.Sabotage.Handlers;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.server.v1_4_5.NBTTagCompound;
import net.minecraft.server.v1_4_5.NBTTagList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_4_5.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.NinjaWolf.Sabotage.Configuration;
import com.github.NinjaWolf.Sabotage.Sabotage;

public class BombHandler {
    static Sabotage plugin;
    public HashMap<Location, Integer> Bombs = new HashMap<Location, Integer>();

    public void handleBombCapture(Block block, final Player player) {
        block.setType(Material.AIR);
        ItemStack bomb = new ItemStack(Material.OBSIDIAN);
        ItemMeta bombMeta = bomb.getItemMeta();

        bombMeta.setDisplayName("§r§bBomb");
        addGlow(bomb);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§aPlant this bomb in");
        lore.add("§ayour enemy's base");
        bombMeta.setLore(lore);
        bomb.setItemMeta(bombMeta);

        player.getInventory().addItem(bomb);
        player.setMetadata("Carrier", new FixedMetadataValue(plugin, true));
        
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                for (Player players: Bukkit.getOnlinePlayers()) {
                    players.setCompassTarget(player.getLocation());
                }
            }
        }, 5 * 20, 5 * 20);
        Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.GREEN + " Grabbed the Bomb!");
    }
    
    public void spawnBomb(int GameID, String name) {
        Block bomb = plugin.arena.getWorld(name).getBlockAt(plugin.arena.getCenterBlock(name));
        Location bombLoc = bomb.getLocation();
        bomb.setType(Material.OBSIDIAN);
        bomb.setMetadata("Bomb", new FixedMetadataValue(plugin, true));
        Configuration.getInstance().getBombConfig().set("Sabotage.Arenas." + name + ".Bomb.x", bombLoc.getBlockX());
        Configuration.getInstance().getBombConfig().set("Sabotage.Arenas." + name + ".Bomb.y", bombLoc.getBlockY());
        Configuration.getInstance().getBombConfig().set("Sabotage.Arenas." + name + ".Bomb.z", bombLoc.getBlockZ());
        Configuration.getInstance().saveBombs();
        this.Bombs.put(bombLoc, GameID);
    }

    public boolean isBomb(Block block) {
        if (!block.hasMetadata("Bomb")) {
            return false;
        }
        int GameID = this.Bombs.get(block.getLocation());
        return this.getBomb(plugin.gameManager.gameInfo.get(GameID)).equals(block.getLocation());
    }
    
    public Location getBomb(String GameName) {
        return new Location(
                plugin.arena.getWorld(GameName),
                Configuration.getInstance().getBombConfig().getInt("Sabotage.Arenas." + GameName + ".Bomb.x"),
                Configuration.getInstance().getBombConfig().getInt("Sabotage.Arenas." + GameName + ".Bomb.y"),
                Configuration.getInstance().getBombConfig().getInt("Sabotage.Arenas." + GameName + ".Bomb.z"));
    }
    
    public static ItemStack addGlow(ItemStack item) {
        net.minecraft.server.v1_4_5.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }
    
    public BombHandler(Sabotage Plugin) {
        plugin = Plugin;
    }
}
