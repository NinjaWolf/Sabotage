package com.github.NinjaWolf.Sabotage.Commands;

import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;
import com.github.NinjaWolf.Sabotage.Utils.Commands;
import com.github.NinjaWolf.Sabotage.Utils.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Leave extends Commands {
    public Leave() {
        super("Leave");
        setDescription("Leaves the Game");
        setUsage("/leave");
        setArgumentRange(0, 1);
        setPermission(Permissions.LEAVE);
        setIdentifiers(new String[] {"leave", "st leave", "sabotage leave"});
    }
    
    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        handleLeaveCommand(sender);
        return true;
    }
    
    private void handleLeaveCommand(CommandSender sender) {
            TeamsHandler.getInstance().leaveGame((Player) sender);
    }
}
