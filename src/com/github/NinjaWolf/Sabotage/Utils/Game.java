package com.github.NinjaWolf.Sabotage.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private int gameID;
    private GameMode mode = GameMode.DISABLED;
    public ArrayList<Game> games = new ArrayList<Game>();
    public final HashMap<Game, String> gameInfo = new HashMap<Game, String>();

    
    public Game(int gameId) {
        this.gameID = gameId;
        
    }
    
    public void startGame(int gameID) {
        
    }
    
    public int getID() {
      return this.gameID;
    }
    
    public String getName(int gameID) {
        return gameInfo.get(gameID);
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
