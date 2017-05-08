package cards;

import java.util.ArrayList;

/**
 * Developer:   Samuel H Wilson
 * Assignment:  Java II Final: Blackjack
 * Date:        05/03/2017
 * File:        DiscardDeck.java
 * Purpose:     Adds discard functionality to deck. Implements discard by remembering live cards and discarded cards. Adds discarded cards back in when deck is exhausted. Live cards are prevented from coming back until discarded.
 * TODO: One day, maybe change to handle discarding individual live cards? Not necessary for current project. Blackjack rules doesn't require this functionality.
 */
public class DiscardDeck extends Deck{
    private ArrayList<Card> liveCards = new ArrayList();
    private ArrayList<Card> discardedCards = new ArrayList();

    //Desc: Changes draw feature to track live cards and handle the discard pile.
    //Does: - Tracks live cards
    //      - Shuffles in discard if empty
    //      - Returns null if deck is empty and no cards are in discard pile
    @Override
    public Card draw() {
        if (!super.canDraw() && discardedCards.size() > 0) {
            java.util.Collections.shuffle(discardedCards);
            super.reintroduce(discardedCards);
            discardedCards.clear();
        }
        Card drawnCard = super.draw();
        liveCards.add(drawnCard);
        return drawnCard;
    }
    
    //Desc: Sends all live cards to the deadCards array.
    public void discardLive() {
        discardedCards.addAll(liveCards);
        liveCards.clear();
    }
    
    //Desc: Override also returns true if there is any cards in the discard pile
    @Override
    public boolean canDraw() {
        return (super.canDraw() || discardedCards.size() > 0);
    }
}
