package testing;
import cards.*;

public class CardTests {
    public static void main(String[] args) {
        //Test one, should print ok looking deck
        Deck test1_d = new Deck();
        int test1_counter = 0;
        while (test1_d.canDraw()) {
            if (test1_counter++ % 13 == 0) {
                System.out.println("");
            }
            System.out.printf(" %4s ", test1_d.draw());
        }
        
        System.out.print("\n");
        //Test tow, should print ok looking shuffled deck
        Deck test2_d = new Deck();
        test2_d.shuffle();
        int test2_counter = 0;
        while (test2_d.canDraw()) {
            if (test2_counter++ % 13 == 0) {
                System.out.println("");
            }
            System.out.printf(" %4s ", test2_d.draw());
        }
    }
}
