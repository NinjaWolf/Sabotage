package com.github.NinjaWolf.Sabotage.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.github.NinjaWolf.Sabotage.Sabotage;
import com.github.NinjaWolf.Sabotage.Handlers.Teams;


public class TagAPI_Listener implements Listener {
    Sabotage Plugin;
    
    @EventHandler
    public void onNameTag(PlayerReceiveNameTagEvent event) {
        Player player = event.getNamedPlayer();
        
        if (Teams.getInstance().inLobby(player.getName())) {
            event.setTag(ChatColor.GREEN + player.getName());
        } else
        
        if (Teams.getInstance().inBlueTeam(player.getName())) {
            event.setTag(ChatColor.DARK_BLUE + player.getName());
        } else
        
        if (Teams.getInstance().inRedTeam(player.getName())) {
            event.setTag(ChatColor.DARK_RED + player.getName());
        }
    }
    
    public TagAPI_Listener(Sabotage instance) {
        Plugin = instance;
    }
}
