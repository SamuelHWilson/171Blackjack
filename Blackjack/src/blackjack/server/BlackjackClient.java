package blackjack.server;

import blackjack.SimplePlayer;
import blackjack.server.bjcorrs.CCorrs;
import blackjack.server.bjcorrs.SCorrs;
import corrserver.CorrConnection;
import corrserver.Correspondence;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackjackClient {
    public static Scanner input = new Scanner(System.in);
    
    SimplePlayer p;
    CorrConnection pcon;
    Thread gameThread;
    
    //TODO: This sucks. Do it right. 
    //              I know, past me, I was low on time. Will fix eventually.
    ArrayList<NSimplePlayer> others = new ArrayList();
    
    public BlackjackClient(String addr, int port) {
        String[] data;
        
        System.out.print("Name: ");
        String name = input.nextLine();
        pcon = CorrConnection.buildFromServer(addr, port);
        
        //Check if connection worked
        data = pcon.requestFromCID(SCorrs.COULD_CONNECT).waitOn();
        if (data[0].equals("true")) {
            //Get server handshake, as of right now it's just the starting balance
            data = pcon.requestFromCID(SCorrs.SERVER_HANDSHAKE).waitOn();
            p = new SimplePlayer(Double.parseDouble(data[0]));
            //Send clinet handshake, as of right not it's just the name
            pcon.send(CCorrs.clientHandshake(name));
            
            System.out.println("Connected."); //tmpr
        }
        
        //Setup responces
        
        // - Recive player list
        pcon.addResponse(SCorrs.TELL_PLAYERS, (String[] rdata) -> {
            //Update player list
            //TODO: Refactor this as well. It's hideous.
            rdata = pcon.requestFromCID(SCorrs.TELL_PLAYERS).waitOn();
            for (int i = 0; i < rdata.length; i++) {
                others.add(new NSimplePlayer(Double.parseDouble(rdata[i]), null));
                others.get(others.size()-1).setName(rdata[++i]);
            }
        });
        
        // - Begin game
        pcon.addResponse(SCorrs.ALERT_START, (String[] rdata) -> {
            gameThread = new Thread(new Blackjack());
            gameThread.start();
        });
        
        //Get player list
        pcon.send(CCorrs.getPlayers());
    }
    
    //Desc: Handles gameplay
    private class Blackjack implements Runnable {
        @Override
        public void run() {
            System.out.println("Game started.");
        }
    }
}
