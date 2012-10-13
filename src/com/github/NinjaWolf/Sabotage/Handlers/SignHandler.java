package com.github.NinjaWolf.Sabotage.Handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class SignHandler {
	
	static SignHandler instance = new SignHandler();
	
	/* Chat Colors */
	ChatColor green = ChatColor.GREEN;
	ChatColor bold = ChatColor.BOLD;
	ChatColor ul = ChatColor.UNDERLINE;
	ChatColor reset = ChatColor.RESET;
	
	  public static SignHandler getInstance() {
	    return instance;
	  }
	
	public void createJoinGameSign(Player player, SignChangeEvent event) {
			gameSign(event);
            event.setLine(2, green + "  [Join Game]   ");
            updateSign(event);
            
            // TODO: Make a Chat Handler to get rid of this mess
            player.sendMessage(green + "[Sabotage]  " + bold + ul + "JOIN" + reset + green +" Game Sign Created Successfully!");
	}
	
	public void createLeaveGameSign(Player player, SignChangeEvent event) {
		gameSign(event);
        event.setLine(2, green + "  [Leave Game]   ");
        updateSign(event);
        
        // TODO: Make a Chat Handler to get rid of this mess
        player.sendMessage(green + "[Sabotage]  " + bold + ul + "LEAVE" + reset + green +" Game Sign Created Successfully!");
	}
	
	
		
	public void gameSign(SignChangeEvent event) {
        event.setLine(0, green + "   [Sabotage]   ");
        event.setLine(1, null);
        event.setLine(2, null);
		event.setLine(3, null);
	}
	
	public void updateSign(SignChangeEvent event) {
		event.getBlock().getState().update();
	}
}
