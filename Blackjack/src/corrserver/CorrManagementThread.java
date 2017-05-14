package corrserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/11/2017
 * File:        CorrManagementThread.java
 * Purpose:     Accepts incoming data and then sends it where it needs to go.
 * TODO: Fix terrible response vs request system.
 */
public class CorrManagementThread {
    private Socket csocket;
    private DataInputStream csocketIn;
    private Thread t;
    private HashMap<Integer, Responce> rc = new HashMap(); //Registered Responces
    private HashMap<Integer, Responce> rr = new HashMap(); //Registered Requests
    
    //Desc: Constructor
    public CorrManagementThread(Socket csocket) {
        this.csocket = csocket;
        try {
            csocketIn = new DataInputStream(csocket.getInputStream());
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't make input stream.");
        }
        t = new Thread(new ListenTask());
        t.start();
    }
    
    //Desc: Adds Corrospondence to rc
    public void RegisterCorrListener(int cid, Responce c) {
        rc.put(cid, c);
    }
    
    //Desc: Listens for incoming data, and then alerts known correspondences.
    //TODO: Stupid proof.
    private class ListenTask implements Runnable {
        private String[] inBuffer;
        private int msize;
        private int cID;
        private boolean stopped = false;
        
        @Override
        public void run() {
            while (!stopped) {
                //Sigh, hate the IOException
                try {
                    //Get cID and message size
                    cID = Integer.parseInt(csocketIn.readUTF());
                    msize = Integer.parseInt(csocketIn.readUTF());
                    inBuffer = new String[msize];
                    
                    //Read all data
                    for (int i = 0; i < msize; i++) {
                        inBuffer[i] = csocketIn.readUTF();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException("Bad utf read");
                }
               
                //try to alert correspondence
                //If there is no correspondence to handle, message is just ignored.
                if (rr.containsKey(cID)) {
                    rr.get(cID).alert(inBuffer);
                    rr.remove(cID);
                }
                else {
                    if (rc.containsKey(cID)) {
                        rc.get(cID).alert(inBuffer);
                    }
                }
            }
        }
    }
}
