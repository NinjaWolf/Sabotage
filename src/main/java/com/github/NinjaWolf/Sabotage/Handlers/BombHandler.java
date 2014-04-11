package com.github.NinjaWolf.Sabotage.Handlers;

import com.github.NinjaWolf.Sabotage.Configuration;
import com.github.NinjaWolf.Sabotage.Sabotage;
import net.minecraft.server.v1_7_R1.NBTTagCompound;
import net.minecraft.server.v1_7_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashMap;

public class BombHandler {
    static Sabotage plugin;
    public HashMap<Location, Integer> Bombs = new HashMap<Location, Integer>();

    public void handleBombCapture(Block block, final Player player) {
        block.setType(Material.AIR);
        ItemStack bomb = new ItemStack(Material.OBSIDIAN);
        ItemStack test = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta bombMeta = bomb.getItemMeta();

        ArrayList<String> lore = new ArrayList<String>();
        lore.add("�aPlant this bomb");
        lore.add("�ain enemy's base");
        bombMeta.setDisplayName("�rBomb");
        bombMeta.setLore(lore);
        bomb.setItemMeta(bombMeta);
        bomb = addGlow(bomb);

        player.getInventory().addItem(bomb);
        player.getInventory().addItem(test);
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
    
    public static ItemStack addGlow(ItemStack item){   
        net.minecraft.server.v1_7_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }
    
    public BombHandler(Sabotage Plugin) {
        plugin = Plugin;
    }
}
