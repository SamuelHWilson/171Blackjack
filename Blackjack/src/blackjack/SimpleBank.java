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
    
    //Desc: subtracts money from balance. Raises exception if balance is bellow 0.
    @Override
    public void withdraw(double toWithdraw) {
        //Check if there is enough money. If not, return false
        if (!canWithdraw(toWithdraw)) {
            throw new IllegalArgumentException(String.format("Bad withdraw. Attempted to withdraw $%.2f from a bank with a balance of $%.2f.", toWithdraw, balance));
        }
        
        balance -= toWithdraw;
    }
}
