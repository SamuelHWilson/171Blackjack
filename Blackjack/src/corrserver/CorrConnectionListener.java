package corrserver;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05//2017
 * File:        CorrConnectionListener.java
 * Purpose:     Details any object that can be informed of an incoming CorrConnection
 */
public interface CorrConnectionListener {
    void alert(CorrConnection cc);
}
