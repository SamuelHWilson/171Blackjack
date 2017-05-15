package blackjack.server;

import blackjack.server.bjcorrs.SCorrs;
import corrserver.CorrConnection;
import corrserver.CorrConnectionListener;
import corrserver.CorrServer;
import java.util.ArrayList;
import blackjack.server.NSimplePlayer;
import blackjack.server.bjcorrs.CCorrs;
import java.util.Collection;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/14/2017
 * File:        BlackjackServer.java
 * Purpose:     Is a blackjack server
 */
public class BlackjackServer {
    private ArrayList<NSimplePlayer> players = new ArrayList();
    private boolean gameRunning = false;
    private Thread gameThread;
    
    private CorrServer server;
    private ServerOutput out;
    
    private final int STARTING_BANK = 5000;
    
    public BlackjackServer(int port, ServerOutput out) {
        server = new CorrServer(port, new BJSConListener());
        out.print("Server started.");
        this.out = out;
    }
    
    //Desc: Listens for new connections, adds them to the list of players, and adds all static Responces
    //TODO: Will break if player never sends handshake corrospondence. Hangs on input forever and keeps other from connecting.
    //TODO: Round start srtup is temporary
    private class BJSConListener implements CorrConnectionListener {
        @Override
        public void alert(CorrConnection c) {
            if (!gameRunning) {
                //Recive player, and get other players
                NSimplePlayer p = new NSimplePlayer(STARTING_BANK, c);
                Collection<NSimplePlayer> others = IsolatePlayer(p);
                
                //Add player to server
                players.add(p);
                
                //Alert player of successful connection
                c.send(SCorrs.couldConnect(true));
                
                //Give Player starting info
                c.send(SCorrs.serverHandshake(STARTING_BANK));
                
                //Get Player's info.
                String[] info = c.requestFromCID(CCorrs.CLIENT_HANDSHAKE).waitOn();
                p.setName(info[0]);
                
                //Alert other players of connection.
                for (NSimplePlayer other: others) {
                    other.getCon().send(SCorrs.playerConnected(other.getName()));
                }
                
                //Setup responses
                c.addResponse(CCorrs.GET_PLAYERS, (String[] rdata) -> {
                    c.send(SCorrs.tellPlayers(players));
                    System.out.println("ran");
                });
                
                c.addResponse(501, (String[] rdata) -> {
                    System.out.println(rdata[0]);
                });
                
                out.print("New player connected: " + p.getName());
                out.print(" - Started with a bankroll of: " + p.getBalance());
                
                if (players.size() > 1) {
                    out.print("Server has two players, locking server and starting game...");
                    startGame();
                }
            } else {
                //Alert player he couldn't connect
                c.send(SCorrs.couldConnect(false));
            }
        }
    }
    
    //Desc: Actually plays Blackjack
    private class Blackjack implements Runnable {
        @Override
        public void run() {
            
        }
    }
    
    //Util ----
    
    private void startGame() {
        gameRunning = true;
        for (NSimplePlayer p: players) {
            p.getCon().send(SCorrs.alertStart());
            System.out.println("   bjs - Sent start message");
        }
        gameThread = new Thread(new Blackjack());
        gameThread.start();
        out.print("Game has started.");
    }
    
    private Collection<NSimplePlayer> IsolatePlayer(NSimplePlayer isp) {
        ArrayList<NSimplePlayer> others = new ArrayList();
        
        for (NSimplePlayer p: players) {
            if (p.getID() != isp.getID()) {
                others.add(p);
            }
        }
        
        return others;
    }
}
