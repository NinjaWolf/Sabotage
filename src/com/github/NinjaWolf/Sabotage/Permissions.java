package com.github.NinjaWolf.Sabotage;

import org.bukkit.entity.Player;

public class Permissions {
    private final static String SABOTAGE = "st";

    public final static String ADMINISTRATION       = SABOTAGE + ".admin";

    private Permissions() {}

    public static boolean hasPermission (Player player, String node) {
        return player.hasPermission(node);
    }
}
