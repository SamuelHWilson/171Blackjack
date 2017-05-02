package blackjack;

import cards.*;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/01/2017
 * File:        Player.java
 * Purpose:     Creates a basic template for all Players. Manages a Hand, a Bank, and carries out Blackjack operations.
 */
public interface Player {
    Hand getHand();
    
    void dealCard(Card c);
    boolean checkBust();
    void getPoints();
    
    void setBet(double bet);
    
    void doubleDown();
    void takeInsurance(double insurance);
    void payInsurance();
    void surrender();
    
    void winHand();
    void winHandBlackjack();
    void loseHand();
    void push();
    
    void resetHand();
}
