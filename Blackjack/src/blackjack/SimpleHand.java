package blackjack;

import cards.Card;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/10/2017
 * File:        SimpleHand.java
 * Purpose:     To provide the simplest possible concrete implementation of Hand.
 */
public class SimpleHand implements Hand{
    private ArrayList<Card> cards = new ArrayList();
    private double bet = 0;
    private double insurance = 0;
    
    private final int[] CARD_VALUES = new int[] {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    
    //Card stuff ---
    
    //Desc: Adds card to hand
    @Override
    public void dealCard(Card c) {
        cards.add(c);
    }
    
    //Desc: Gets Collection<Card> of all cards.
    @Override
    public Collection<Card> getCards() {
        return cards;
    }
    
    //Desc: Gets count of cards in hand.
    @Override
    public int getCardCount() {
        return cards.size();
    }
    
    //Desc: Gets card at index.
    @Override
    public Card getCard(int index) {
        return cards.get(index);
    }
    
    //Desc: Calculates and returns hand value based on blackjack rules
    //Does: 1. Adds together point values of all cards
    //      2. Counts the aces
    //      3. If hand exceeds 21, reduce ace values to 1 point.
    @Override
    public int getHandValue() {
        int aces = 0;
        int value = 0;
        
        //Go over all cards
        for (Card c: cards) {
            if (!(c.getRank() == 0)) { //If card is not an ace, just count it
                value += CARD_VALUES[c.getRank()];
            } else { //If it is, tick aces, add 11 to value
                value += 11;
                aces++;               
            }
        }
        
        //If hand value exceeds 21, try to reduce ace values to 1 until value is 21 or bellow
        //Doesn't even run unless value > 21 AND player has at least one ace.
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        
        return value;
    }
    
    //Bet stuff ---
    
    //Desc: Sets bet
    @Override
    public void setBet(double bet) {
        this.bet = bet;
    }
    
    //Desc: Adds toIncrease to bet
    @Override
    public void increaseBet(double toIncrease) {
        bet += toIncrease;
    }
    
    //Desc: Gets bet
    @Override
    public double getBet() {
        return bet;
    }
    
    
    //Insurance stuff ---
    
    //Desc: Sets insurance
    @Override
    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }
    
    //Desc: Gets insurance
    @Override
    public double getInsurance() {
        return insurance;
    }
}
