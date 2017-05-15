package corrserver;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/14/2017
 * File:        MissedCorrespondence.java
 * Purpose:     Used to correct an issue with Request correspondences. Basically a correspondence + a time stamp.
 */
public class MissedCorrespondence extends Correspondence{
    private long ts;
    
    private final long GRACE_PERIOD = 1000;
    
    //Constructor
    public MissedCorrespondence(int cID, String[] data, long ts) {
        super(cID, data);
        this.ts = ts;
    }
    
    //Desc: Returns true if MissedCorrespondence is new enough to use.
    public boolean didExpire(long curTs) {
        return (curTs - ts) > GRACE_PERIOD;
    }
}
