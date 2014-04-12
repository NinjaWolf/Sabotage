package com.github.NinjaWolf.Sabotage.Commands;

import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Utils.Commands;
import com.github.NinjaWolf.Sabotage.Utils.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Create extends Commands {
    public Create(Sabotage Plugin) {
        super("Create");
        setDescription("Creates Object binded with Specified Name.");
        setUsage("/create <Type> <Name>");
        setArgumentRange(2, 2);
        setPermission(Permissions.CREATION);
        setIdentifiers(new String[] {"create", "st create", "sabotage create"});
        plugin = Plugin;
    }
    
    public Sabotage plugin;
    

    public boolean execute(CommandSender sender, String identifier, String[] args) {
        List<String> types = Arrays.asList("ARENA");
        if (!types.contains(args[0].toUpperCase(Locale.ENGLISH))) {
            return false;
        }
        
        if (args[0].equalsIgnoreCase("Arena")) {
            plugin.arenaHandler.createArena((Player)sender, args[1]);
            return true;
        } else
        return false;
    }
}
