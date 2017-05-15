package blackjack;
import cards.*;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/10/2017
 * File:        SimplePlayer.java
 * Purpose:     Provides the simplest possible concrete implementation of a Player.
 */
public class SimplePlayer implements Player {
    private Hand h = new SimpleHand();
    private Bank b = new SimpleBank();
    
    private final double WIN_PAYOUT = 2.0;
    private final double BLACKJACK_PAYOUT = 2.5;
    private final double INSURANCE_PAYOUT = 2.0;
    private final double SURRENDER_PAYOUT = 0.5;
    
    
    //Constructor ---
    public SimplePlayer(double startingBankroll) {
        b.setBalance(startingBankroll);
    }
    
    //Hand interface ---
    
    //Desc: Gets hand
    @Override
    public Hand getHand() {
        return h;
    }
    
    //Desc: Gets points from hand acording to Blackjack rules
    @Override
    public int getPoints() {
        return h.getHandValue();
    }
    
    //Desc: Adds card to hand
    @Override
    public void dealCard(Card c) {
        h.dealCard(c);
    }
    
    //Bank interface ---
    @Override
    public double getBalance() {
        return b.getBalance();
    }
    
    @Override
    public void setBalance(double balance) {
        b.setBalance(balance);
    }
    
    //Rules interface ---
    
    //Desc: Checks to see if player's hand is over 21
    @Override
    public boolean checkBust() {
        return h.getHandValue() > 21;
    }
    
    //Desc: Checks to see if the player got a Blackjack
    @Override
    public boolean checkBlackjack() {
        return (h.getCardCount() == 2 && h.getHandValue() == 21);
    }
    
    //Desc: Sets hand bet
    //Does: Withdraws bet from bank and adds bet to hand
    @Override
    public void setBet(double bet) {
        b.withdraw(bet);
        h.setBet(bet);
    }
    
    //Side rules interface ---
    
    //Desc: Doubles bet
    @Override
    public void doubleDown() {
        b.withdraw(h.getBet());
        h.increaseBet(h.getBet());
    }
    
    //Desc: Takes out insurance, costs 0.5 times bet
    @Override
    public void takeInsurance(double insurance) {
        h.setInsurance(insurance);
    }
    
    //Desc: Surrenders hand
    //Does: Keep half of bet, lose rest
    @Override
    public void surrender() {
        b.deposit(h.getBet() * SURRENDER_PAYOUT);
    }
    
    //Payouts ---
    
    //Desc: Win payout
    @Override
    public void winHand() {
        b.deposit(h.getBet() * WIN_PAYOUT);
    }
    
    //Desc: Blackjack payout
    @Override
    public void winHandBlackjack() {
        b.deposit(h.getBet() * BLACKJACK_PAYOUT);
    }
    
    //Desc: Get bet back
    @Override
    public void push() {
        b.deposit(h.getBet());
    }
    
    //Desc: Gets insurance payout
    @Override
    public void payInsurance() {
        b.deposit(h.getInsurance() * INSURANCE_PAYOUT);
    }
    
    //Rest ---
    
    //Desc: Creates whole new hand. Bank is unafected.
    @Override
    public void resetHand() {
        h = new SimpleHand();
    }
}
