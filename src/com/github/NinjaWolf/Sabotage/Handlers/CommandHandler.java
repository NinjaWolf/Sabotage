package com.github.NinjaWolf.Sabotage.Handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.NinjaWolf.Sabotage.Commands.StCommand;
import com.github.NinjaWolf.Sabotage.Utils.Permissions;


public class CommandHandler {
    private final Map<String, StCommand> commands    = new LinkedHashMap<String, StCommand>();
    private final Map<String, StCommand> identifiers = new HashMap<String, StCommand>();
    
    public void addCommand(StCommand command) {
        commands.put(command.getName().toLowerCase(), command);
        for (String ident : command.getIdentifiers()) {
            identifiers.put(ident.toLowerCase(), command);
        }
    }
    
    public boolean dispatch(CommandSender sender, String label, String[] args) {
        for (int argsIncluded = args.length; argsIncluded >= 0; argsIncluded--) {
            StringBuilder identifier = new StringBuilder(label);
            for (int i = 0; i < argsIncluded; i++) {
                identifier.append(" ").append(args[i]);
            }
            
            StCommand cmd = getCmdFromIdent(identifier.toString(), sender);
            if (cmd != null)
            {
                String[] realArgs = Arrays.copyOfRange(args, argsIncluded, args.length);
                
                if (!cmd.isInProgress(sender)) {
                    if ((realArgs.length < cmd.getMinArguments()) || (realArgs.length > cmd.getMaxArguments())) {
                        displayCommandHelp(cmd, sender);
                        return true;
                    }
                    if ((realArgs.length > 0) && (realArgs[0].equals("?"))) {
                        displayCommandHelp(cmd, sender);
                        return true;
                    }
                }
                
                if ((!cmd.getPermission().isEmpty()) && (!Permissions.hasPermission((Player) sender, cmd.getPermission()))) {
                    sender.sendMessage("Insufficient permission.");
                    return true;
                }
                
                cmd.execute(sender, identifier.toString(), realArgs);
                return true;
            }
        }
        return true;
    }
    
    private void displayCommandHelp(StCommand cmd, CommandSender sender) {
        sender.sendMessage(new StringBuilder().append("§cCommand:§e ").append(cmd.getName()).toString());
        sender.sendMessage(new StringBuilder().append("§cDescription:§e ").append(cmd.getDescription()).toString());
        sender.sendMessage(new StringBuilder().append("§cUsage:§e ").append(cmd.getUsage()).toString());
        if (cmd.getNotes() != null) {
            for (String note : cmd.getNotes()) {
                sender.sendMessage(new StringBuilder().append("§e").append(note).toString());
            }
        }
    }
    
    public StCommand getCmdFromIdent(String ident, CommandSender executor)
    {
        ident = ident.toLowerCase();
        if (identifiers.containsKey(ident))
            return (StCommand) identifiers.get(ident);
        
        for (StCommand cmd : commands.values()) {
            if (cmd.isIdentifier(executor, ident))
                return cmd;
        }
        
        return null;
    }
    
    public StCommand getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
    
    public List<StCommand> getCommands() {
        return new ArrayList<StCommand>(commands.values());
    }
    
    public void removeCommand(StCommand command) {
        commands.remove(command.getName().toLowerCase());
        for (String ident : command.getIdentifiers()) {
            identifiers.remove(ident.toLowerCase());
        }
    }
}
