package testing;

import corrserver.CorrConnection;
import corrserver.CorrServer;
import corrserver.Correspondence;
import java.util.ArrayList;

public class CorrTests {
    static ArrayList<CorrConnection> serverCons = new ArrayList();
    public static void main(String[] args) {
        CorrServer server = new CorrServer(9000, (CorrConnection c) -> {
            serverCons.add(c);
            c.addResponse(0, (String[] s) -> {
                for (String str: s) {
                    System.out.println(str);
                }
            });
        });
        CorrConnection con1 = CorrConnection.buildFromServer("localhost", 9000);
        
        con1.send(new Correspondence(0, "one", "two", "three"));
    }
}
