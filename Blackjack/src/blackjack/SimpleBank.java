package blackjack;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/10/2017
 * File:        SimpleBank.java
 * Purpose:     Provides the simplest possible concrete implementation of bank.
 */
public class SimpleBank implements Bank {
    private double balance;
    
    //Desc: Sets balance
    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    //Desc: Gets blance
    @Override
    public double getBalance() {
        return balance;
    }
    
    //Desc: Adds money to balance
    @Override
    public void deposit(double toDeposit) {
        balance += toDeposit;
    }
    
    //Desc: Checks to see if money can be taken from balance
    @Override
    public boolean canWithdraw(double toWithdraw) {
        return toWithdraw <= balance;
    }
    
    //Desc: Attempts to take moeny from balance, returns true if success false if not
    @Override
    public boolean attemptWithdraw(double toWithdraw) {
        //Check if there is enough money. If not, return false
        if (!canWithdraw(toWithdraw)) {
            return false;
        }
        
        balance -= toWithdraw;
        return true;
    }
}
