package com.github.NinjaWolf.Sabotage.Utils;

import org.bukkit.entity.Player;


public class Permissions {
    private final static String SABOTAGE       = "st";
    
    public final static String  ADMINISTRATION = SABOTAGE + ".admin";

    public final static String  JOIN           = SABOTAGE + ".join";
    public final static String  LEAVE          = SABOTAGE + ".leave";
    public final static String  HELP           = SABOTAGE + ".help";
    
    private Permissions() {}
    
    public static boolean hasPermission(Player player, String perm) {
        return player.hasPermission(perm);
    }
}
