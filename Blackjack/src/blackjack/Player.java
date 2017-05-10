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
    //Hand interface
    Hand getHand();
    int getPoints();  
    void dealCard(Card c);
    
    //Bank interface
    double getBalance();
    
    //Regular Rules
    boolean checkBust();
    void setBet(double bet);
    
    //Side rules
    void doubleDown();
    void takeInsurance(double insurance);
    void surrender();
    
    //Playouts
    void winHand();
    void winHandBlackjack();
    void push();
    void payInsurance();
    
    //New round
    void resetHand();
}
