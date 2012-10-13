package com.github.NinjaWolf.Sabotage.Handlers;

import org.bukkit.ChatColor;

public class Teams {

	public static String teamName;
	public static ChatColor teamColor;
	public static Teams instance = new Teams(teamName, teamColor);
	
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
        if (TeamsHandler.getInstance().lobbyList.contains(playerName)) { 
            return true;
        }
        return false;
    }
    
    public boolean inBlueTeam(String playerName) {
        if (TeamsHandler.getInstance().blueList.contains(playerName)) { 
            return true;
        }
        return false;
    }
    
    public boolean inRedTeam(String playerName) {
        if (TeamsHandler.getInstance().redList.contains(playerName)) { 
            return true;
        }
        return false;
    }
    
}
