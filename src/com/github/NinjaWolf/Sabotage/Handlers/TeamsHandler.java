package com.github.NinjaWolf.Sabotage.Handlers;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

import com.github.NinjaWolf.Sabotage.Sabotage;


public class TeamsHandler {
    
    public HashMap<String, String> Teams    = new HashMap<String, String>();
    public Teams                   Red;
    public Teams                   Blue;
    static Sabotage                st;
    static TeamsHandler            instance = new TeamsHandler(st);
    
    ChatColor                      red      = ChatColor.RED;
    ChatColor                      blue     = ChatColor.BLUE;
    ChatColor                      green    = ChatColor.GREEN;
    ChatColor                      bold     = ChatColor.BOLD;
    ChatColor                      reset    = ChatColor.RESET;
    
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
            Teams.put(player.getName(), "Blue");
            TagAPI.refreshPlayer(player);
            player.sendMessage(blue + "Welcome to the " + bold + "BLUE" + reset + blue + " team!");
        } else if (team == "Red") {
            Teams.put(player.getName(), "Red");
            TagAPI.refreshPlayer(player);
            player.sendMessage(red + "Welcome to the " + bold + "RED" + reset + red + " team!");
        }
    }
    
    public void addToLobby(Player player) {
        Bukkit.getServer().getWorld("world").setSpawnLocation(10, 66, 10);
        Location Lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        Teams.put(player.getName(), "Lobby");
        player.teleport(Lobby);
        player.sendMessage(green + "Welcome Back to the " + bold + "Lobby");
        TagAPI.refreshPlayer(player);
    }
    
    public void removeFromTeam(Player player) {
        if (!Teams.get(player.getName()).isEmpty()) {
            Teams.put(player.getName(), null);
        }
        
        player.sendMessage(green + "You have left the team.");
        addToLobby(player);
    }
    
    public void joinGame(Player player) {
        if (isInGame(player)) {
            player.sendMessage(green + "You are already in a Team.");
            return;
        }
        
        Random random = new Random();
        int teamsIndex = random.nextInt(100);
        String team = null;
        
        if (teamsIndex <= 24) {
            team = "Blue";
        } else if (teamsIndex <= 49 && teamsIndex >= 25) {
            team = "Red";
        } else if (teamsIndex <= 74 && teamsIndex >= 50) {
            team = "Blue";
        } else if (teamsIndex <= 99 && teamsIndex >= 75) {
            team = "Red";
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
        if ((Teams.get(player.getName()) == "Red") || (Teams.get(player.getName()) == "Blue")) {
            return true;
        }
        return false;
    }
    
}
