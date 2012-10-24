package com.github.NinjaWolf.Sabotage.Handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.NinjaWolf.Sabotage.Permissions;
import com.github.NinjaWolf.Sabotage.Commands.StCommand;
import com.github.NinjaWolf.Sabotage.Utils.Commands;

public class CommandHandler {
    private Map<String, Commands> commands = new LinkedHashMap<String, Commands>();
    private Map<String, Commands> identifiers = new HashMap<String, Commands>();

  public  void addCommand(Commands command) {
    this.commands.put(command.getName().toLowerCase(), command);
    for (String ident : command.getIdentifiers())
      this.identifiers.put(ident.toLowerCase(), command);
  }

  public boolean dispatch(CommandSender sender, String label, String[] args)
  {
    for (int argsIncluded = args.length; argsIncluded >= 0; argsIncluded--) {
      StringBuilder identifier = new StringBuilder(label);
      for (int i = 0; i < argsIncluded; i++) {
        identifier.append(" ").append(args[i]);
      }

      Commands cmd = getCmdFromIdent(identifier.toString(), sender);
      if (cmd != null)
      {
        String[] realArgs = (String[])Arrays.copyOfRange(args, argsIncluded, args.length);

        if (!cmd.isInProgress(sender)) {
          if ((realArgs.length < cmd.getMinArguments()) || (realArgs.length > cmd.getMaxArguments())) {
            displayCommandHelp(cmd, sender);
            return true;
          }if ((realArgs.length > 0) && (realArgs[0].equals("?"))) {
            displayCommandHelp(cmd, sender);
            return true;
          }
        }

        if ((!cmd.getPermission().isEmpty()) && (!Permissions.hasPermission((Player)sender, cmd.getPermission()))) {
          sender.sendMessage("Insufficient permission.");
          return true;
        }

        cmd.execute(sender, identifier.toString(), realArgs);
        return true;
      }
    }
    return true;
  }

  private void displayCommandHelp(Commands cmd, CommandSender sender) {
    sender.sendMessage(new StringBuilder().append("§cCommand:§e ").append(cmd.getName()).toString());
    sender.sendMessage(new StringBuilder().append("§cDescription:§e ").append(cmd.getDescription()).toString());
    sender.sendMessage(new StringBuilder().append("§cUsage:§e ").append(cmd.getUsage()).toString());
    if (cmd.getNotes() != null)
      for (String note : cmd.getNotes())
        sender.sendMessage(new StringBuilder().append("§e").append(note).toString());
  }

  public Commands getCmdFromIdent(String ident, CommandSender executor)
  {
    ident = ident.toLowerCase();
    if (this.identifiers.containsKey(ident)) {
      return (Commands)this.identifiers.get(ident);
    }

    for (Commands cmd : this.commands.values()) {
      if (cmd.isIdentifier(executor, ident)) {
        return cmd;
      }
    }

    return null;
  }

  public Commands getCommand(String name) {
    return (Commands)this.commands.get(name.toLowerCase());
  }

  public List<Commands> getCommands() {
    return new ArrayList<Commands>(this.commands.values());
  }

  public void removeCommand(Commands command) {
    this.commands.remove(command.getName().toLowerCase());
    for (String ident : command.getIdentifiers())
      this.identifiers.remove(ident.toLowerCase());
  }

public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

  Player player = null;
  if ((sender instanceof Player)) {
    player = (Player)sender;
  }
  else {
    System.out.println("Only ingame players can use Sabotage commands");
    return true;
  }

  if (commandLabel.equalsIgnoreCase("help")) {
     help(player);
     return true;
   }
  return false;
}

public void help(Player p) {
  p.sendMessage("/<command> <args>");
  for (StCommand v : this.commands.values())
    p.sendMessage(ChatColor.AQUA + v.help(p));
}
}