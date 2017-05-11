package blackjack;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/01/2017
 * File:        Bank.java
 * Purpose:     Creates a basic template for all banks. They are used to keep track of Player's money.
 */
public interface Bank {
    void setBalance(double balance);
    double getBalance();
    void deposit(double toDeposit);
    boolean canWithdraw(double checkWithdraw);
    void withdraw(double toWithdraw);
}
