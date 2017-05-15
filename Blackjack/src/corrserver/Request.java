package corrserver;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/14/2017
 * File:        Request.java
 * Purpose:     Waits for a one time response from the CorrServer.
 */
public class Request implements Response {
    private String[] data = null;
    private final Object notifier = new Object();
    
    //Desc: Returns data as soon as it's recived, works like a getter if it was already alerted.
    public String[] waitOn() {
        if (data == null) {
            synchronized(notifier) {
                try {
                    notifier.wait();
                } catch (InterruptedException ex) {
                    throw new RuntimeException("waitOn got interupted.");
                }
            }
        }
        
        return data;
    }
    
    //Desc: Notifies that data has been recived
    @Override
    public void alert(String[] data) {
        synchronized(notifier) {
            this.data = data;
            notifier.notify();
        }
    }
    
    //Desc: Builds and registeres new Request from a CorrConnection and a cID
    public static Request fromCID(int cID, CorrConnection con) {
        Request temp = new Request();
        con.addOneTimeResponse(cID, temp);
        return temp;
    }
}
