package com.github.NinjaWolf.Sabotage.Commands;

import com.github.NinjaWolf.Sabotage.Handlers.TeamsHandler;
import com.github.NinjaWolf.Sabotage.Utils.Commands;
import com.github.NinjaWolf.Sabotage.Utils.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Join extends Commands {
    public Join() {
        super("Join");
        setDescription("Joins the Game");
        setUsage("/join");
        setArgumentRange(0, 1);
        setPermission(Permissions.JOIN);
        setIdentifiers(new String[] {"join", "st join", "sabotage join"});
    }
    
    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        handleJoinCommand(sender);
        return true;
    }
    
    private void handleJoinCommand(CommandSender sender) {
            TeamsHandler.getInstance().joinGame((Player) sender);
    }
}
