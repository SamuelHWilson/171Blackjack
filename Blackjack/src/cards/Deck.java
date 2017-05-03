package cards;

import java.util.ArrayList;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/03/2017
 * File:        Deck.java
 * Purpose:     Simulates a deck of 52 playing cards.   
 */
public class Deck {
    public ArrayList<Card> deckArray;
    
    //Desc: Constructs a new Deck with 52 cards.
    public Deck() {
        deckArray = new ArrayList();
        
        for (int i = 0; i < 52; i++) {
            deckArray.add(new Card(i));
        }
    }
    
    //Desc: Randomly shuffles array
    public void shuffle() {
        java.util.Collections.shuffle(deckArray);
    }
    
    //Desc: Draws top card from deck
    public Card draw() {
        return deckArray.remove(0);
    }
    
    //Desc: Returns true if there is a card to draw
    public boolean canDraw() {
        return deckArray.size() != 0;
    }
}
