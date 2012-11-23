package com.github.NinjaWolf.Sabotage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.NinjaWolf.Sabotage.Commands.Help;
import com.github.NinjaWolf.Sabotage.Commands.Join;
import com.github.NinjaWolf.Sabotage.Commands.Leave;
import com.github.NinjaWolf.Sabotage.Generator.Arena_Generator;
import com.github.NinjaWolf.Sabotage.Handlers.CommandHandler;
import com.github.NinjaWolf.Sabotage.Listeners.BlockListener;
import com.github.NinjaWolf.Sabotage.Listeners.PlayerListener;
import com.github.NinjaWolf.Sabotage.Listeners.TagAPI_Listener;


public class Sabotage extends JavaPlugin {
    
    private final PlayerListener        playerListener = new PlayerListener();
    private final BlockListener         blockListener  = new BlockListener();
    private final TagAPI_Listener       tagapiListener = new TagAPI_Listener(this);
    public final Configuration          config         = new Configuration(this);
    private static final CommandHandler commandHandler = new CommandHandler();
    
    public final File                   mainConfig     = new File(getDataFolder(), "config.yml");
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHandler.dispatch(sender, label, args);
    }
    
    @Override
    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        
        if (!mainConfig.exists()) {
            getConfig().options().copyDefaults(true);
            config.save();
        }
        registerListeners();
        registerCommands();
        
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
            getLogger().log(Level.INFO, "Metrics Loaded.");
        } catch (IOException e) {
            getLogger().log(Level.INFO, "Metrics Couldn't Be Loaded.");
        }
        getLogger().log(Level.INFO, "Version: " + pdfFile.getVersion() + " is now Enabled.");
    }
    
    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvents(playerListener, this);
        pm.registerEvents(blockListener, this);
        
        if (isActive("TagAPI")) {
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
    
    private void registerCommands() {
        commandHandler.addCommand(new Join());
        commandHandler.addCommand(new Leave());
        commandHandler.addCommand(new Help());
    }
    
    public boolean isActive(String plugin) {
        if (getServer().getPluginManager().getPlugin(plugin) != null)
            return true;
        return false;
    }
    
    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new Arena_Generator();
    }
}
