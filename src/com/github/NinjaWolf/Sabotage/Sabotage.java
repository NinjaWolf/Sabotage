package com.github.NinjaWolf.Sabotage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.NinjaWolf.Sabotage.Commands.Create;
import com.github.NinjaWolf.Sabotage.Commands.Help;
import com.github.NinjaWolf.Sabotage.Commands.Join;
import com.github.NinjaWolf.Sabotage.Commands.Leave;
import com.github.NinjaWolf.Sabotage.Commands.ListPlayers;
import com.github.NinjaWolf.Sabotage.Generator.Arena_Generator;
import com.github.NinjaWolf.Sabotage.Handlers.ArenaHandler;
import com.github.NinjaWolf.Sabotage.Handlers.BombHandler;
import com.github.NinjaWolf.Sabotage.Handlers.CommandHandler;
import com.github.NinjaWolf.Sabotage.Listeners.BlockListener;
import com.github.NinjaWolf.Sabotage.Listeners.PlayerListener;
import com.github.NinjaWolf.Sabotage.Listeners.TagAPI_Listener;
import com.github.NinjaWolf.Sabotage.Utils.Arena;
import com.github.NinjaWolf.Sabotage.Utils.Game;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;


public class Sabotage extends JavaPlugin {
    
    private final PlayerListener        playerListener = new PlayerListener(this);
    private final BlockListener         blockListener  = new BlockListener();
    private final TagAPI_Listener       tagapiListener = new TagAPI_Listener(this);
    public final Configuration          config         = new Configuration(this);
    public final ArenaHandler           arenaHandler   = new ArenaHandler(this);
    public final BombHandler            bombHandler    = new BombHandler(this);
    public final Arena                  arena          = new Arena(this);
    public final Game                   gameManager    = new Game(this);
    private static final CommandHandler commandHandler = new CommandHandler();
    private static WorldEditPlugin worldEditPlugin;
    
    File mainConfig = new File(getDataFolder(), "config.yml");
    File bomb = new File(getDataFolder(), "bomb.yml");
    File arenas = new File(getDataFolder(), "arenas.yml");
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHandler.dispatch(sender, label, args);
    }
    
    @Override
    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        
        loadConfigs();

        registerListeners();
        registerCommands();
        
        setupWorldEdit();
        setupMetrics();
        
        getLogger().log(Level.INFO, "Version " + pdfFile.getVersion() + " is enabled.");
    }
    
    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvents(playerListener, this);
        pm.registerEvents(blockListener, this);
        
        if (isEnabled("TagAPI")) {
            pm.registerEvents(tagapiListener, this);
            getLogger().log(Level.INFO, "TagAPI Loaded Successfully.");
        } else {
            /* 
             * TODO: Make a Method to use if TagAPI isn't found
             *      This way Colored Nametags will still work.
             *      Just send a Entity-Destroy Packet then a 
             *      Entity-Create Packet with the Players name
             *      in color.
             */
            getLogger().log(Level.INFO, "TagAPI couldn't be found, Colored Nametags disabled");
        }
    }
    
    protected void registerCommands() {
        commandHandler.addCommand(new Join());
        commandHandler.addCommand(new Leave());
        commandHandler.addCommand(new ListPlayers());
        commandHandler.addCommand(new Create(this));
        commandHandler.addCommand(new Help());
    }
    
    public void loadConfigs() {
        if (!mainConfig.exists()) {
            getConfig().options().copyDefaults(true);
        }
        
        if (!bomb.exists()) {
            config.reloadBombConfig();
        }
        
        if (!arenas.exists()) {
            config.reloadArenaConfig();
        }

    }
    
    private void setupWorldEdit() {
        Plugin p = getServer().getPluginManager().getPlugin("WorldEdit");
        if (p != null && p instanceof WorldEditPlugin) {
            worldEditPlugin = (WorldEditPlugin) p;
        }
    }
    
    private void setupMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().log(Level.INFO, "Metrics Couldn't Be Loaded.");
        }
    }
    
    public boolean isEnabled(String plugin) {
        if (getServer().getPluginManager().getPlugin(plugin) != null) {
            return true;
        }
            return false;
    }
    
    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }
    
    public WorldEditPlugin getWorldEdit() {
        return worldEditPlugin;
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new Arena_Generator();
    }
}
