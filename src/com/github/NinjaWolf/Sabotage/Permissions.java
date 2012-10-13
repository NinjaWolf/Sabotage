package com.github.NinjaWolf.Sabotage;

import org.bukkit.entity.Player;

public class Permissions {
    private final static String SABOTAGE = "st";

    public final static String ADMINISTRATION       = SABOTAGE + ".admin";
    public final static String JOIN					= SABOTAGE + ".join";
    public final static String LEAVE				= SABOTAGE + ".leave";

    private Permissions() {}

    public static boolean hasPermission (Player player, String perm) {
        return player.hasPermission(perm);
    }
}
