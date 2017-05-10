package blackjack;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/10/2017
 * File:        SimpleBank.java
 * Purpose:     Provides the simplest possible concrete implementation of bank.
 */
public class SimpleBank implements Bank {
    private int balance;
    
    //Desc: Sets balance
    @Override
    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    //Desc: Gets blance
    @Override
    public int getBalance() {
        return balance;
    }
    
    //Desc: Adds money to balance
    @Override
    public void deposit(int toDeposit) {
        balance += toDeposit;
    }
    
    //Desc: Checks to see if money can be taken from balance
    @Override
    public boolean canWithdraw(int toWithdraw) {
        return toWithdraw <= balance;
    }
    
    //Desc: Attempts to take moeny from balance, returns true if success false if not
    @Override
    public boolean attemptWithdraw(int toWithdraw) {
        //Check if there is enough money. If not, return false
        if (!canWithdraw(toWithdraw)) {
            return false;
        }
        
        balance -= toWithdraw;
        return true;
    }
}
