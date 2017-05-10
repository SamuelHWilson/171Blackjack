package blackjack;

import cards.*;
import java.util.Collection;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/01/2017
 * File:        Hand.java
 * Purpose:     Creates a basic template for all Hands. Hands are used to manage cards and bets for a round.
 */
public interface Hand {
    void dealCard(Card c);
    Collection<Card> getCards();
    
    int GetHandValue();
    
    void setBet(int bet);
    void increaseBet(int toIncrease);
    int getBet();
    
    void setInsurance(int insurance);
    int getInsurance();
}
