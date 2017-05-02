package blackjack;

import cards.*;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/01/2017
 * File:        Hand.java
 * Purpose:     Creates a basic template for all Hands. Hands are used to manage cards and bets for a round.
 */
public interface Hand {
    void dealCard(Card c);
    void removeCard(Card c);
    CardCollection getCards();
    
    int GetHandValue();
    
    void setBet(double bet);
    void increaseBet(double toIncrease);
    double getBet();
    
    void setInsurance(double insurance);
    double getInsurance();
}
