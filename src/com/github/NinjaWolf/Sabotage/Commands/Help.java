package com.github.NinjaWolf.Sabotage.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Utils.Commands;
import com.github.NinjaWolf.Sabotage.Utils.Permissions;

public class Help extends Commands {
    public Help() {
        super("Sabotage");
        setDescription("Displays Commands for Sabotage.");
        setUsage("/sabotage");
        setArgumentRange(0, 1);
        setPermission(Permissions.HELP);
        setIdentifiers(new String[] {"sabotage", "st", "sabotage help", "st help", "help"});
    }
    
    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        int page = 0;
        if (args.length != 0)
          try {
            page = Integer.parseInt(args[0]) - 1;
          }
          catch (NumberFormatException ignored)
          {
          }
        List<StCommand> sortCommands = Sabotage.getCommandHandler().getCommands();
        List<StCommand> commands = new ArrayList<StCommand>();

        for (StCommand command : sortCommands) {
          if ((command.isShownOnHelpMenu()) && 
            (Permissions.hasPermission((Player)sender, command.getPermission()))) {
            commands.add(command);
          }

        }

        int numPages = commands.size() / 8;
        if (commands.size() % 8 != 0) {
          numPages++;
        }
        if (numPages == 0) {
          numPages = 1;
        }

        if ((page >= numPages) || (page < 0)) {
          page = 0;
        }
        sender.sendMessage("§c-----[ §aSabotage Help §f<" + (page + 1) + "/" + numPages + ">§c ]-----");
        int start = page * 8;
        int end = start + 8;
        if (end > commands.size()) {
          end = commands.size();
        }
        for (int c = start; c < end; c++) {
          StCommand cmd = commands.get(c);
          sender.sendMessage("  §a" + cmd.getUsage() + "§f - §a" + cmd.getDescription());
        }
        return true;
      }
}
