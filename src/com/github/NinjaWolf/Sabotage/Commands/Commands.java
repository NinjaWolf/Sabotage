package com.github.NinjaWolf.Sabotage.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class Commands implements CommandExecutor {
    Sabotage plugin;
    
    public Commands(Sabotage instance) {
        plugin = instance;
        
    }
    
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        return false;
    }
}
