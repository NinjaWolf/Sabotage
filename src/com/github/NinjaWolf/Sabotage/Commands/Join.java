package com.github.NinjaWolf.Sabotage.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.NinjaWolf.Sabotage.Permissions;
import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;
import com.github.NinjaWolf.Sabotage.Utils.Commands;


public class Join extends Commands {
    public Join() {
        super("Join");
        setDescription("Joins the Game");
        setUsage("/join");
        setArgumentRange(0, 1);
        setIdentifiers(new String[] {"join", "st join", "sabotage join"});
    }
    
    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        handleJoinCommand(sender);
        return true;
    }
    
    private void handleJoinCommand(CommandSender sender) {
        if (Permissions.hasPermission((Player) sender, Permissions.JOIN)) {
            TeamsHandler.getInstance().joinGame((Player) sender);
        }
        
    }
    
    @Override
    public String help(Player player) {
        return "/join - Join the Game";
    }
}
