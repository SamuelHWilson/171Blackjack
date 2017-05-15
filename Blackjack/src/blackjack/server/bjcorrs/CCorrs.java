package blackjack.server.bjcorrs;

import corrserver.Correspondence;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/14/2017
 * File:        SCorrs.java
 * Purpose:     Provides static methods used to make valid BlackjackClient correspondences.
 * Notes:       BJ Client cIDs range from 100 - 199
 */
public class CCorrs {
    //Desc: tells server info about the clinet
    public static final int CLIENT_HANDSHAKE = 100;
    public static Correspondence clientHandshake(String name) {
        return new Correspondence(100, name);
    }
    
    //Desc: Requests server to send player list
    public static final int GET_PLAYERS = 101;
    public static Correspondence getPlayers() {
        return new Correspondence(101, "flag");
    }
}
