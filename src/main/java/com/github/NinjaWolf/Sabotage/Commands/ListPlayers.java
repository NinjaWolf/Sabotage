package com.github.NinjaWolf.Sabotage.Commands;

import com.github.NinjaWolf.Sabotage.Handlers.Teams;
import com.github.NinjaWolf.Sabotage.Utils.Commands;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListPlayers extends Commands {
    public ListPlayers() {
        super("List");
        setDescription("Displays List of Online Players.");
        setUsage("/list");
        setArgumentRange(0, 1);
        setIdentifiers(new String[] {"list", "st list", "sabotage list"});
    }  
    
    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        /*TODO: See if there's a way to scrap these arraylist.
         * EDIT: There is, use the old method for method of it,
         * just use .replaceAll to fix the old issues.
         * ITs a little bit messy, but it gets rid of having to
         * loop thru the 3 arrays every time command is issued.
        */
        List<String> bluePlayers= new ArrayList<String>();
        List<String> redPlayers= new ArrayList<String>();
        List<String> lobbyPlayers= new ArrayList<String>();

        for(Player player: Bukkit.getOnlinePlayers()) {
            if (Teams.getInstance().inBlueTeam(player.getName())) {
                bluePlayers.add("�1" + player.getName() + "�r");
            }
            
            if (Teams.getInstance().inRedTeam(player.getName())) {
                redPlayers.add("�4" + player.getName() + "�r");
            }
            
            if (Teams.getInstance().inLobby(player.getName())) {
                lobbyPlayers.add("�a" + player.getName() + "�r");
            }
        }

            sender.sendMessage("�a�l�nSabotage:");
            sender.sendMessage("");
            sender.sendMessage("�9�l    Blue:" + "(" + bluePlayers.size() + " Players)");
            
            sender.sendMessage("        " + bluePlayers.toString().replaceAll("[\\['']|['\\]'']", ""));
            
            sender.sendMessage("�c�l    Red:" + "(" + redPlayers.size() + " Players)");

            sender.sendMessage("        " + redPlayers.toString().replaceAll("[\\['']|['\\]'']", ""));
            
            sender.sendMessage("�a�l    Lobby:" + "(" + lobbyPlayers.size() + " Players)");
            
            sender.sendMessage("        " + lobbyPlayers.toString().replaceAll("[\\['']|['\\]'']", ""));
            return true;
    }
}
