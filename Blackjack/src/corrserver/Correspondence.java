package corrserver;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/12/2017
 * File:        Correspondence.java
 * Purpose:     Provide a constructor and access methods for a structured message.
 */
public class Correspondence {
    private int cID;
    private String[] data;
    
    //Desc: Constructor
    //TODO: Fix so flags do not require any data
    public Correspondence(int cID, String... data) {
        this.cID = cID;
        this.data = data;
    }
    
    //Getters: 
    public int getCID() {
        return cID;
    }
    
    public String[] getData() {
        return data;
    }
}
