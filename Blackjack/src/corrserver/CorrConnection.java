package corrserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/11/2017
 * File:        CorrConnection.java
 * Purpose:     Provides an interface between the user and the Corrospondence Connection Thread.
 */
public class CorrConnection {
    private CorrManagementThread cmt;
    private Socket csocket;
    private DataOutputStream csocketOut;
    
    //Desc: Constructor
    public CorrConnection(Socket csocket) {
        this.csocket = csocket;
        cmt = new CorrManagementThread(csocket);
        try {
            csocketOut = new DataOutputStream(csocket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException("Could not get output stream.");
        }
    }
    
    //Desc: Adds Responce to the CorrManagementThread
    public void addCorrListener(int cID, Responce cl) {
        cmt.RegisterCorrListener(cID, cl);
    }
    
    //Desc: Sends Correspondence to server
    public void send(Correspondence c) {
        //Sight, HATE IOException
        try {
            csocketOut.writeUTF(String.valueOf(c.getCID()));
            csocketOut.writeUTF(String.valueOf(c.getData().length));
            for (String s: c.getData()) {
                csocketOut.writeUTF(s);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Bad send.");
        }
    }
    
    //Desc: Builds new CorrConnection as a new clinet connection
    public static CorrConnection buildFromServer(String addr, int port) {
        try {
            Socket temp = new Socket(addr, port);
            return new CorrConnection(temp);
        } catch (IOException ex) {
            throw new RuntimeException("Could not connect to server");
        }
    }
}
