package corrserver;

/**
 * Developer:   Samuel H Wilson
 Assignment:  Java II Final: Blackjack
 Date:        05/11/2017
 File:        Response.java
 Purpose:     Functional interface. Defines any object that can be alerted and receive a String[].
 *              Also includes persistent flag.
 */
public interface Response {
    void alert(String[] data);
}
