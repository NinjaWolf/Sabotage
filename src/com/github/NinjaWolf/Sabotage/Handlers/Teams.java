package com.github.NinjaWolf.Sabotage.Handlers;

import org.bukkit.ChatColor;


public class Teams {
    
    public static String      teamName;
    public static ChatColor   teamColor;
    public static Teams       instance    = new Teams(teamName, teamColor);
    public final TeamsHandler teamHandler = TeamsHandler.getInstance();
    
    public static Teams getInstance() {
        return instance;
    }
    
    public Teams(String n1, ChatColor c2) {
        teamName = n1;
        teamColor = c2;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public ChatColor getTeamColor() {
        return teamColor;
    }
    
    public boolean inLobby(String playerName) {
        return teamHandler.Teams.get(playerName).equals(0);
    }
    
    public boolean inBlueTeam(String playerName) {
        return teamHandler.Teams.get(playerName).equals(1);
    }
    
    public boolean inRedTeam(String playerName) {
        return teamHandler.Teams.get(playerName).equals(2);
    }
    
}
