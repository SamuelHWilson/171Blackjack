package corrserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private HashMap<Integer, Response> rc = new HashMap(); //Registered Responces
    private HashMap<Integer, Response> rotc = new HashMap(); //Registered one-time responces.
    private MissedCorrespondence lastMissed = new MissedCorrespondence(-1, new String[] {}, 0);
    
    private Boolean registering = false;
    
    //Used for debugging
    private static int readID = 0;
    
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
    
    //Desc: Adds Corrospondence to rc, first checks to see if there was any missed
    public void RegisterResponse(int cID, Response c) {
        if (cID == lastMissed.getCID() && !lastMissed.didExpire(System.currentTimeMillis())) {
            c.alert(lastMissed.getData());
        }
        rc.put(cID, c);
    }
    
    //Desc: Adds Corrospondence to rotc, first checks to see if there was any missed, if there was, it skips adding the r
    public void RegisterOneTimeResponse(int cID, Response c) {
//        System.out.println("   cmt - Registered Request");
        if (cID == lastMissed.getCID() && !lastMissed.didExpire(System.currentTimeMillis())) {
            c.alert(lastMissed.getData());
        } else {
            rotc.put(cID, c);
        }
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
                    System.out.println("   cmt - Starting read" + sReadID());
                    cID = Integer.parseInt(csocketIn.readUTF());
                    System.out.println("   cmt - Read id: " + cID + sReadID());
                    msize = Integer.parseInt(csocketIn.readUTF());
                    System.out.println("   cmt - Read size: " + msize + sReadID());
                    inBuffer = new String[msize];
                    
                    //Read all data
                    for (int i = 0; i < msize; i++) {
                        inBuffer[i] = csocketIn.readUTF();
                    }
                    System.out.println("   cmt - Read data: " + inBuffer.length + " items" + sReadID());
                    tickReadID();
                } catch (IOException ex) {
                    throw new RuntimeException("Bad utf read");
                }
               
                //try to alert correspondence
                //If there is no correspondence to handle, it is saved as last missed.
//                System.out.println("   cmt - Tried to alert Response for: " + cID);
                synchronized (registering) {
                    if (registering) {
                        System.out.println("   cmt - Caught registering, will wait.");
                        try {
                            registering.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CorrManagementThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (rotc.containsKey(cID)) {
                    System.out.println("   cmt - One Time Responce found");
                    rotc.get(cID).alert(inBuffer);
                    rotc.remove(cID);
                } else if (rc.containsKey(cID)) {
                    System.out.println("   cmt - Responce found");
                    rc.get(cID).alert(inBuffer);
                } else { //Only runs if correspondence isn't registered
                    System.out.println("   cmt - No responce found, setting to lastMissed");
                    lastMissed = new MissedCorrespondence(cID, inBuffer, System.currentTimeMillis());
                }
            }
        }
    }
    
    private static String sReadID() {
        return " (" + readID + ")";
    }
    
    private static void tickReadID() {
        readID++;
    }
    
}
