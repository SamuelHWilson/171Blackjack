package blackjack;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/01/2017
 * File:        Bank.java
 * Purpose:     Creates a basic template for all banks. They are used to keep track of Player's money.
 */
public interface Bank {
    void setBalance(int balance);
    int getBalance();
    void deposit(int toDeposit);
    boolean canWithdraw(int checkWithdraw);
    boolean attemptWithdraw(int toWithdraw);
}
