package cards;

import java.util.ArrayList;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/03/2017
 * File:        Shoe.java
 * Purpose:     Allows for multiple decks worth of cards in a DiscardDeck.
 */
public class Shoe extends DiscardDeck {
    //Desc: Prevents accidental call of parent constructor
    private Shoe() {
        
    }
    
    //Desc: Constructs DiscardDeck with extra cards
    //TODO: Maybe add validation of deckNum
    public Shoe(int deckNum) {
        ArrayList<Card> cardsToAdd = new ArrayList();
        
        for (int i = 1; i < deckNum; i++) {
            for (int j = 0; j < 52; j++) {
                cardsToAdd.add(new Card(j));
            }
        }
        
        super.reintroduce(cardsToAdd); //Semantically wrong, but it works.
    }
    
    
}
