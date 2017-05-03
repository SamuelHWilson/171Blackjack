package testing;
import cards.*;

public class CardTests {
    public static void main(String[] args) {
        //Test one, should print ok looking deck
        System.out.println("One");
        Deck test1_d = new Deck();
        int test1_counter = 0;
        while (test1_d.canDraw()) {
            if (test1_counter++ % 13 == 0) {
                System.out.println("");
            }
            System.out.printf(" %4s ", test1_d.draw());
        }
        
        System.out.print("\n");
        
        //Test two, should print ok looking shuffled deck
        System.out.println("Two");
        Deck test2_d = new Deck();
        test2_d.shuffle();
        int test2_counter = 0;
        while (test2_d.canDraw()) {
            if (test2_counter++ % 13 == 0) {
                System.out.println("");
            }
            System.out.printf(" %4s ", test2_d.draw());
        }
        
        System.out.print("\n");
        
        //Test three, should print whole twice, once shuffled, without freaking out. Proves DiscardDeck works because the seconds set of cards must have all come from the discard pile.
        System.out.println("Three");
        DiscardDeck test3_d = new DiscardDeck();
        int test3_counter = 0;
        while (test3_d.canDraw()) {
            if (test3_counter++ % 13 == 0) {
                System.out.println("");
            }
            System.out.printf(" %4s ", test3_d.draw());
        }
        test3_d.discardLive();
        while (test3_d.canDraw()) {
            if (test3_counter++ % 13 == 0) {
                System.out.println("");
            }
            System.out.printf(" %4s ", test3_d.draw());
        }
        
    }
}
