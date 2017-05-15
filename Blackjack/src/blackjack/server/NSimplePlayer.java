package blackjack.server;

import blackjack.SimplePlayer;
import corrserver.CorrConnection;

/**
 * Developer:   Samuel H Wilson
 Assignment:  Java II Final: Blackjack
 Date:        05/14/2017
 File:        NSimplePlayer.java
 Purpose:     Adds code to make SimplePlayer server friendly.
 */
public class NSimplePlayer extends SimplePlayer {
    public static int playerCount = 0;
    
    private CorrConnection con;
    private String name = "Anon";
    private int id = 0;
    
    public NSimplePlayer(double startingBankroll, CorrConnection con) {
        super(startingBankroll);
        id = NSimplePlayer.playerCount++;
        this.con = con;
    }
    
    //Getters
    public CorrConnection getCon() {
        return con;
    }
    
    public String getName() {
        return name;
    }
    
    public int getID() {
        return id;
    }
    
    //Setters
    public void setName(String name) {
        this.name = name;
    }
}
