package blackjack.server.bjcorrs;

import blackjack.Player;
import blackjack.server.NSimplePlayer;
import corrserver.Correspondence;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/14/2017
 * File:        SCorrs.java
 * Purpose:     Provides static methods used to make valid BlackjackServer correspondences.
 * Notes:       BJ Server cIDs range from 0 - 99
 * TODO: Organize
 */
public class SCorrs {
    //Desc: Tells user if connection succeded, either true or false
    public static final int COULD_CONNECT = 0;
    public static Correspondence couldConnect(boolean couldConnect) {
        return new Correspondence(0, String.valueOf(couldConnect));
    }
    
    //Desc: Tells users that a new player connected
    public static final int PLAYER_CONNECTED = 1;
    public static Correspondence playerConnected(String name) {
        return new Correspondence(1, name);
    }
    
    //Desc: Tells the user information they need to start
    public static final int SERVER_HANDSHAKE = 2;
    public static Correspondence serverHandshake(double startingBank) {
        return new Correspondence(2, String.valueOf(startingBank));
    }
    
    //Desc: Tells the user what players are connected, and how much money they have
    public static final int TELL_PLAYERS = 3;
    public static Correspondence tellPlayers(ArrayList<NSimplePlayer> players) {
        String[] data = new String[players.size() * 2];
        
        int index = 0;
        for (NSimplePlayer p: players) {
            data[index++] = String.valueOf(p.getBalance());
            data[index++] = p.getName();
        }
        
        return new Correspondence(3, data);
    }
    
    //Desc: Alerts players that game is starting
    public static final int ALERT_START = 4;
    public static Correspondence alertStart() {
        return new Correspondence(4, "flag");
    }
}
