package testing;

import blackjack.server.BlackjackServer;
import corrserver.CorrConnection;
import corrserver.Request;

public class BJServerTests {
    public static void main(String[] args) {
        String[] data;
        BlackjackServer server = new BlackjackServer(9000, (String s) -> {
            System.out.println(s);
        });
    }
}
