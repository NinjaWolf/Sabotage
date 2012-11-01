package com.github.NinjaWolf.Sabotage.Handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

import com.github.NinjaWolf.Sabotage.Sabotage;


public class TeamsHandler {
    
    public HashMap<String, List<String>> Teams     = new HashMap<String, List<String>>();
    public Teams                         Red;
    public Teams                         Blue;
    static Sabotage                      st;
    static TeamsHandler                  instance  = new TeamsHandler(st);
    
    ChatColor                            red       = ChatColor.RED;
    ChatColor                            blue      = ChatColor.BLUE;
    ChatColor                            green     = ChatColor.GREEN;
    ChatColor                            bold      = ChatColor.BOLD;
    ChatColor                            reset     = ChatColor.RESET;
    
    List<String>                         blueList  = new ArrayList<String>();
    List<String>                         redList   = new ArrayList<String>();
    List<String>                         lobbyList = new ArrayList<String>();
    
    public static TeamsHandler getInstance() {
        return instance;
    }
    
    public TeamsHandler(Sabotage Main) {
        st = Main;
        Red = new Teams("Red", ChatColor.RED);
        Blue = new Teams("Blue", ChatColor.BLUE);
    }
    
    public void addToTeam(String team, Player player) {
        if (team == "Blue") {
            blueList.add(player.getName());
            lobbyList.remove(player.getName());
            TagAPI.refreshPlayer(player);
            player.sendMessage(blue + "Welcome to the " + bold + "BLUE" + reset + blue + " team!");
        } else if (team == "Red") {
            redList.add(player.getName());
            lobbyList.remove(player.getName());
            TagAPI.refreshPlayer(player);
            player.sendMessage(red + "Welcome to the " + bold + "RED" + reset + red + " team!");
        }
        Teams.put("Blue", blueList);
        Teams.put("Red", redList);
        Teams.put("Lobby", lobbyList);
    }
    
    public void addToLobby(Player player) {
        Bukkit.getServer().getWorld("world").setSpawnLocation(10, 66, 10);
        Location Lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        lobbyList.add(player.getName());
        Teams.put("Lobby", lobbyList);
        player.teleport(Lobby);
        player.sendMessage(green + "Welcome Back to the " + bold + "Lobby");
        TagAPI.refreshPlayer(player);
    }
    
    public void removeFromTeam(Player player) {
        if (blueList.contains(player.getName())) {
            blueList.remove(player.getName());
        } else if (redList.contains(player.getName())) {
            redList.remove(player.getName());
        }
        Teams.put("Blue", blueList);
        Teams.put("Red", redList);
        player.sendMessage(green + "You have left the team.");
        addToLobby(player);
    }
    
    public boolean teamIsEmpty(String team) {
        if (Teams.get(team).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    public void joinGame(Player player) {
        if (isInGame(player)) {
            player.sendMessage(green + "You are already in a Team.");
            return;
        }
        
        Random random = new Random();
        int teamsIndex = random.nextInt(4);
        String team = null;
        
        switch (teamsIndex) {
            case 0:
                team = "Blue";
                break;
            case 1:
                team = "Red";
                break;
            case 2:
                team = "Red";
                break;
            case 3:
                team = "Blue";
                break;
        }
        
        addToTeam(team, player);
        
    }
    
    public void leaveGame(Player player) {
        if (!isInGame(player)) {
            player.sendMessage(green + "You aren't currently in a Team.");
            return;
        }
        
        removeFromTeam(player);
    }
    
    public boolean isInGame(Player player) {
        if ((redList.contains(player.getName()) || (blueList.contains(player.getName())))) {
            return true;
        }
        return false;
    }
    
}
