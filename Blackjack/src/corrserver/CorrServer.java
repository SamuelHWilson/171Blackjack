package corrserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/11/2017
 * File:        CorrServer.java
 * Purpose:     To accept incoming connections, initialize a CorrManagementThread for them, and alert the user.
 */
public class CorrServer {
    private ServerSocket ssocket;
    private CorrConnectionListener ccl;
    private Thread listenThread;
    
    private ArrayList<CorrConnection> connections = new ArrayList();
    private boolean running = true;
    
    //Desc: Constructor
    public CorrServer(int port, CorrConnectionListener ccl) {
        try {
            ssocket = new ServerSocket(port);
        } catch (IOException ex) {
            throw new RuntimeException("Tried to bind CorrServer to bad port. Could not bind to port: " + port);
        }
        
        this.ccl = ccl;
        listenThread = new Thread(new ListenTask());
        listenThread.start();
    }
    
    //Desc: Listens for new connections and then sets them up.
    private class ListenTask implements Runnable {
        public void run() {
            while (running) {
                Socket csocket;
                try {
                    csocket = ssocket.accept();
                } catch (IOException ex) {
                    throw new RuntimeException("Cleint tried to connect but could not.");
                }

                connections.add(new CorrConnection(csocket));
                ccl.alert(connections.get(connections.size()-1));
            }
        }
    }
}
