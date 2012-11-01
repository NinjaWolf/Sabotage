package com.github.NinjaWolf.Sabotage.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.NinjaWolf.Sabotage.Permissions;
import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;
import com.github.NinjaWolf.Sabotage.Utils.Commands;


public class Leave extends Commands {
    public Leave() {
        super("Leave");
        setDescription("Leaves the Game");
        setUsage("/leave");
        setArgumentRange(0, 1);
        setIdentifiers(new String[] {"leave", "st leave", "sabotage leave"});
    }
    
    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        handleLeaveCommand(sender);
        return true;
    }
    
    private void handleLeaveCommand(CommandSender sender) {
        if (Permissions.hasPermission((Player) sender, Permissions.LEAVE)) {
            TeamsHandler.getInstance().leaveGame((Player) sender);
        }
        
    }
    
    @Override
    public String help(Player player) {
        return "/leave - Leave the game";
    }
    
}
