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
    
    public  final HashMap<String, String> Teams    = new HashMap<String, String>();
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
    
    public void addToTeam(String team, Player player) {
        String redName = red + player.getName() + reset;
        String blueName = blue + player.getName() + reset;
        if (team == "Blue") {
            Teams.put(player.getName(), "Blue");
            player.setDisplayName(blueName);
            player.setPlayerListName(blueName);
            TagAPI.refreshPlayer(player);
            player.sendMessage(blue + "You have joined the " + bold + "BLUE" + reset + blue + " team!");
        } else if (team == "Red") {
            Teams.put(player.getName(), "Red");
            player.setDisplayName(redName);
            player.setPlayerListName(redName);
            TagAPI.refreshPlayer(player);
            player.sendMessage(red + "You have joined the " + bold + "RED" + reset + red + " team!");
        }
    }
    
    public void addToLobby(Player player) {
        Bukkit.getServer().getWorld("world").setSpawnLocation(10, 64, 10);
        Location Lobby = Bukkit.getServer().getWorld("world").getSpawnLocation();
        
        Teams.put(player.getName(), "Lobby");
        player.setDisplayName(green + player.getName() + reset);
        player.setPlayerListName(green + player.getName());
        player.teleport(Lobby);
        TagAPI.refreshPlayer(player);
    }
    
    public void removeFromTeam(Player player) {
        if (Teams.get(player.getName()).equals("Lobby")) {
            Teams.remove(player.getName());
            return;
        }
        if (Teams.containsKey(player.getName()))
            Teams.remove(player.getName());
            
            addToLobby(player);
            player.sendMessage(green + "You have left the team. Welcome Back to the " + bold + "Lobby.");
    }
    
    public void leaveGame(Player player) {
        if (!isInGame(player)) {
            player.sendMessage(green + "You aren't currently in a Team.");
            return;
        }
        
        removeFromTeam(player);
    }
    
    public boolean isInGame(Player player) {
        if (Teams.get(player.getName()).equals("Red") || Teams.get(player.getName()).equals("Blue")) {
            return true;
        }
        return false;
    }
    
}
