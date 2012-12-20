package com.github.NinjaWolf.Sabotage.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;

import com.github.NinjaWolf.Sabotage.Sabotage;

public class Game {

    Sabotage plugin;
    private Arena arena;
    private int gameID;
    private GameMode mode = GameMode.DISABLED;
    public ArrayList<Game> games = new ArrayList<Game>();
    public HashMap<Integer, String> gameInfo = new HashMap<Integer, String>();

    
    public Game(int gameId) {
        this.gameID = gameId;
    }
    public Game(Sabotage Plugin) {
        plugin = Plugin;
    }
    
    public void load() {
        //int x = Configuration.getInstance().getArenaConfig().getInt("Sabotage.Arenas." + getArena(this.gameID) + ".x1");
        //int y = Configuration.getInstance().getArenaConfig().getInt("Sabotage.Arenas." + getArena(this.gameID) + ".y1");
        //int z = Configuration.getInstance().getArenaConfig().getInt("Sabotage.Arenas." + getArena(this.gameID) + ".z1");
        //int x1 = Configuration.getInstance().getArenaConfig().getInt("Sabotage.Arenas." + getArena(this.gameID) + ".x2");
        //int y1 = Configuration.getInstance().getArenaConfig().getInt("Sabotage.Arenas." + getArena(this.gameID) + ".y2");
        //int z1 = Configuration.getInstance().getArenaConfig().getInt("Sabotage.Arenas." + getArena(this.gameID) + ".z2");
        //Location max = new Location(plugin.arena.getWorld(this.gameID), Math.max(x, x1), Math.max(y, y1), Math.max(z, z1));
        //Location min = new Location(plugin.arena.getWorld(this.gameID), Math.min(x, x1), Math.min(y, y1), Math.min(z, z1));

        //this.arena = new Arena(min, max);
    }
    
    public int getID() {
      return this.gameID;
    }
    
    public String getArena(int gameID) {
        return this.gameInfo.get(gameID);
    }
    
    public boolean isBlockInArena(Location v) {
      return this.arena.containsBlock(v);
    }
    
    public void setMode(GameMode m) {
        this.mode = m;
    }

    public GameMode getGameMode() {
        return this.mode;
    }
    public static enum GameMode {
      DISABLED, LOADING, INPROGRESS, WAITING, 
      STARTING, FINISHING, RESETING, ERROR;
    }

}
