package com.github.NinjaWolf.Sabotage.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Handlers.Teams;

import org.kitteh.tag.PlayerReceiveNameTagEvent;


public class TagAPI_Listener implements Listener {
    Sabotage Main;
    
    @EventHandler
    public void onNameTag(PlayerReceiveNameTagEvent event) {
        Player player = event.getNamedPlayer();
        
        if (Teams.getInstance().inLobby(player.getName())) {
            event.setTag(ChatColor.GREEN + player.getName());
        }
        
        if (Teams.getInstance().inBlueTeam(player.getName())) {
            event.setTag(ChatColor.BLUE + player.getName());
        }
        
        if (Teams.getInstance().inRedTeam(player.getName())) {
            event.setTag(ChatColor.RED + player.getName());
        }
    }
    
    public TagAPI_Listener(Sabotage instance) {
        Main = instance;
    }
}
