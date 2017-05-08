package cards;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/03/2017
 * File:        Deck.java
 * Purpose:     Simulates a deck of 52 playing cards.   
 */
public class Deck {
    private ArrayList<Card> deckArray = new ArrayList();
    
    //Desc: Constructs a new Deck with 52 cards.
    public Deck() {      
        for (int i = 0; i < 52; i++) {
            deckArray.add(new Card(i));
        }
    }
    
    //Desc: Randomly shuffles array
    public void shuffle() {
        java.util.Collections.shuffle(deckArray);
    }
    
    //Desc: Draws top card from deck. Returns null if can't
    public Card draw() {
        if (canDraw()) return deckArray.remove(0);
        else return null;
    }
    
    //Desc: Returns true if there is a card to draw
    public boolean canDraw() {
        return deckArray.size() != 0;
    }
    
    //Desc: Adds internal method for reintroducing cards to the deck. Example: See DiscardDeck
    protected void reintroduce(Collection<Card> cards) {
        deckArray.addAll(cards);
    }
}
