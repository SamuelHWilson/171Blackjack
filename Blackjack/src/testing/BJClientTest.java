package testing;

import blackjack.server.BlackjackClient;
import java.util.Scanner;

public class BJClientTest {
    public static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
        BlackjackClient bjc = new BlackjackClient("localhost", 9000);
    }
}
